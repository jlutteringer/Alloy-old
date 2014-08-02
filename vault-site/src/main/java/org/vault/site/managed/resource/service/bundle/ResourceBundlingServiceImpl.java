package org.vault.site.managed.resource.service.bundle;

import java.io.ByteArrayOutputStream;
import java.util.Collection;
import java.util.Map;

import org.alloy.metal.domain.Path;
import org.alloy.metal.resource._Resource;
import org.alloy.metal.spring.AlloyBean;
import org.alloy.metal.utilities._Exception;
import org.alloy.metal.utilities._Closeable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.vault.site.managed.resource.service.VaultResourceResolverService;
import org.vault.site.resource.GeneratedResource;
import org.vault.site.resource.bundle.Bundle;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;

@Service
public class ResourceBundlingServiceImpl extends AlloyBean implements ResourceBundlingService {
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
		return _Exception.propagate(() -> {
			byte[] result = _Closeable.withResult(new ByteArrayOutputStream(), (baos) -> {
				logger.debug("Creating bundle for resources " + bundles.get(name));
				for (String resourceLocation : bundles.get(name)) {
					Resource resource = resourceResolver.getResource(Path.of(resourceLocation));

					_Resource.getInputStream(resource, (inputStream) -> {
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

			if (logger.isTraceEnabled()) {
				logger.trace("Created bundle:");
				logger.trace(new String(result));
			}
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