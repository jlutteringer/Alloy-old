package org.vault.site.managed.resource.service;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.vault.base.spring.beans.VaultBean;
import org.vault.core.managed.cache.service.StatisticsService;
import org.vault.core.managed.file.domain.FileWorkArea;
import org.vault.core.managed.file.service.VaultFileService;
import org.vault.site.resource.GeneratedResource;
import org.vault.site.resource.handler.VaultResourceHandler;

@Service
public class ResourceBundlingServiceImpl extends VaultBean implements ResourceBundlingService {
	// Map of known versioned bundle names ==> the resources that are part of that bundle
	// ex: "global12345.js" ==> [Resource("/js/BLC.js"), Resource("/js/blc-admin.js")]
	protected Map<String, Collection<Resource>> bundles = new HashMap<String, Collection<Resource>>();

	// Map of known unversioned bundle names ==> additional files that should be included
	// Configured via XML
	// ex: "global.js" ==> ["classpath:/file1.js", "/js/file2.js"]
	protected Map<String, List<String>> additionalBundleFiles = new HashMap<String, List<String>>();

	@Autowired
	protected VaultFileService fileService;

	@Autowired
	protected ResourceMinificationService minifyService;

	@Autowired
	protected StatisticsService statisticsService;

	@Override
	public Resource getBundle(String versionedBundleName) {
		// If we can find this bundle on the file system, we've already generated it
		// and we don't need to do so again.
		Resource r = readBundle(versionedBundleName);
		if (r != null && r.exists()) {
			return r;
		}

		// Otherwise, we'll create the bundle, write it to the file system, and return
		r = createBundle(versionedBundleName);
		saveBundle(r);
		return r;
	}

	protected Resource readBundle(String versionedBundleName) {
		File bundleFile = fileService.getLocalResource(getResourcePath(versionedBundleName));
		return new FileSystemResource(bundleFile);
	}

	/**
	* Returns the resource path for the given <b>name</b> in URL-format (meaning, / separators)
	* @param name
	* @return
	*/
	protected String getResourcePath(String name) {
		return "bundles/" + name;
	}

	protected Resource createBundle(String versionedBundleName) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] bytes = null;

		// Join all of the resources for this bundle together into a byte[]
		try {
			for (Resource r : bundles.get(versionedBundleName)) {
				InputStream is = null;

				try {
					is = r.getInputStream();
					StreamUtils.copy(is, baos);
				} catch (IOException e) {
					throw new RuntimeException(e);
				} finally {
					try {
						is.close();
					} catch (IOException e2) {
						throw new RuntimeException(e2);
					}
				}

				// If we're creating a JavaScript bundle, we'll put a semicolon between each
				// file to ensure it won't fail to compile.
				if (versionedBundleName.endsWith(".js")) {
					baos.write(";".getBytes());
				}
				baos.write(System.getProperty("line.separator").getBytes());
			}
			bytes = baos.toByteArray();
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				baos.close();
			} catch (IOException e2) {
				throw new RuntimeException(e2);
			}
		}

		// Minify the resource
		byte[] minifiedBytes = minifyService.minify(versionedBundleName, bytes);

		// Create our GenerateResource that holds our combined and (potentially) minified bundle
		GeneratedResource r = new GeneratedResource(minifiedBytes, versionedBundleName);
		return r;
	}

	protected void saveBundle(Resource resource) {
		FileWorkArea tempWorkArea = fileService.initializeWorkArea();
		String tempFilename = FilenameUtils.concat(tempWorkArea.getFilePathLocation(), FilenameUtils.separatorsToSystem(getResourcePath(resource.getDescription())));
		File tempFile = new File(tempFilename);
		if (!tempFile.getParentFile().exists()) {
			if (!tempFile.getParentFile().mkdirs()) {
				if (!tempFile.getParentFile().exists()) {
					throw new RuntimeException("Unable to create parent directories for file: " + tempFilename);
				}
			}
		}

		BufferedOutputStream out = null;
		InputStream ris = null;
		try {
			ris = resource.getInputStream();
			out = new BufferedOutputStream(new FileOutputStream(tempFile));
			StreamUtils.copy(ris, out);

			ris.close();
			out.close();

			fileService.addOrUpdateResource(tempWorkArea, tempFile, true);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			IOUtils.closeQuietly(ris);
			IOUtils.closeQuietly(out);
			fileService.closeWorkArea(tempWorkArea);
		}
	}

	@Override
	public boolean hasBundle(String versionedBundle) {
		return bundles.containsKey(versionedBundle);
	}

	@Override
	public synchronized String registerBundle(String bundleName, List<String> files, VaultResourceHttpRequestHandler handler) throws IOException {
		LinkedHashMap<String, Resource> foundResources = new LinkedHashMap<String, Resource>();

		if (additionalBundleFiles.get(bundleName) != null) {
			files.addAll(additionalBundleFiles.get(bundleName));
		}

		for (String file : files) {
			boolean match = false;

			// Check to see if there is any registered handler that understands how to generate
			// this file.
			if (handler.getHandlers() != null) {
				for (VaultResourceHandler h : handler.getHandlers()) {
					if (h.canHandle(file)) {
						foundResources.put(file, h.getResource(file, handler.getLocations()));
						match = true;
						break;
					}
				}
			}

			// If we didn't find an override for this file, let's see if we can
			// look it up from our known locations
			if (!match) {
				for (Resource location : handler.getLocations()) {
					try {
						Resource resource = location.createRelative(file);
						if (resource.exists() && resource.isReadable()) {
							foundResources.put(file, resource);
							match = true;
							break;
						}
					} catch (IOException ex) {
						logger.debug("Failed to create relative resource - trying next resource location", ex);
					}
				}
			}
		}

		String version = getBundleVersion(foundResources);
		String versionedName = getBundleName(bundleName, version);

		bundles.put(versionedName, foundResources.values());
		return versionedName;
	}

	protected String getCacheKey(String unversionedBundleName) {
		return unversionedBundleName;
	}

	protected String getBundleName(String bundleName, String version) {
		String bundleWithoutExtension = bundleName.substring(0, bundleName.lastIndexOf('.'));
		String bundleExtension = bundleName.substring(bundleName.lastIndexOf('.'));
		String versionedName = bundleWithoutExtension + version + bundleExtension;
		return versionedName;
	}

	protected String getBundleVersion(LinkedHashMap<String, Resource> foundResources) throws IOException {
		StringBuilder sb = new StringBuilder();
		for (Entry<String, Resource> entry : foundResources.entrySet()) {
			sb.append(entry.getKey());

			if (entry.getValue() instanceof GeneratedResource) {
				sb.append(((GeneratedResource) entry.getValue()).getHashRepresentation());
			} else {
				sb.append(entry.getValue().lastModified());
			}

			sb.append("\r\n");
		}
		String version = String.valueOf(sb.toString().hashCode());
		return version;
	}

	@Override
	public List<String> getAdditionalBundleFiles(String bundleName) {
		return additionalBundleFiles.get(bundleName);
	}

	public Map<String, List<String>> getAdditionalBundleFiles() {
		return additionalBundleFiles;
	}

	public void setAdditionalBundleFiles(Map<String, List<String>> additionalBundleFiles) {
		this.additionalBundleFiles = additionalBundleFiles;
	}

	@Override
	public Map<String, Collection<Resource>> getBundles() {
		return bundles;
	}
}
