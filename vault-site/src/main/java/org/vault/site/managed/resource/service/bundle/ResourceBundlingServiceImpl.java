package org.vault.site.managed.resource.service.bundle;

import java.io.ByteArrayOutputStream;
import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.vault.base.closeable.VCloseables;
import org.vault.base.request.Path;
import org.vault.base.resource.VResources;
import org.vault.base.spring.beans.VaultBean;
import org.vault.base.utilities.exception.Exceptions;
import org.vault.site.managed.resource.service.VaultResourceResolverService;
import org.vault.site.resource.GeneratedResource;
import org.vault.site.resource.bundle.Bundle;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;

@Service
public class ResourceBundlingServiceImpl extends VaultBean implements ResourceBundlingService {
	private Map<String, String> pathToBundle = Maps.newHashMap();
	private Multimap<String, String> bundles = HashMultimap.create();

	@Autowired
	private VaultResourceResolverService resourceResolver;

	@Override
	public boolean hasBundleForResource(String path) {
		return pathToBundle.containsKey(path);
	}

	@Override
	public String getBundleNameForResource(String location) {
		return pathToBundle.get(location);
	}

	@Override
	public boolean hasBundleForName(String name) {
		return bundles.containsKey(name);
	}

	@Override
	public Resource resolveBundleForName(String name) {
		return Exceptions.propagate(() -> {
			byte[] result = VCloseables.withResult(new ByteArrayOutputStream(), (baos) -> {
				for (String resourceLocation : bundles.get(name)) {
					Resource resource = resourceResolver.getResource(Path.of(resourceLocation));

					VResources.getInputStream(resource, (inputStream) -> {
						StreamUtils.copy(inputStream, baos);
					});

					// FUTURE handle this better
					// If we're creating a JavaScript bundle, we'll put a semicolon between each
					// file to ensure it won't fail to compile.
					if (resourceLocation.endsWith(".js")) {
						baos.write(";".getBytes());
					}
					baos.write(System.getProperty("line.separator").getBytes());
				}

				return baos.toByteArray();
			});

			return new GeneratedResource(result, name);
		});
	}

	@Override
	public Map<String, Collection<String>> getBundles() {
		return bundles.asMap();
	}

	@Override
	public void registerBundle(Bundle bundle) {
		bundles.putAll(bundle.getBundleName(), bundle.getBundleResourceLocations());
		bundle.getBundleResourceLocations().forEach((resourceLocation) -> pathToBundle.put(resourceLocation, bundle.getBundleName()));
	}
}