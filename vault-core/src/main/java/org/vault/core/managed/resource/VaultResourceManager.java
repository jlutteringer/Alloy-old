package org.vault.core.managed.resource;

import java.util.List;

import org.alloy.forge.module.Module;
import org.alloy.forge.module.ModuleLoader;
import org.alloy.metal.collections.iterable._Iterable;
import org.alloy.metal.collections.lists._List;
import org.alloy.metal.function._Function;
import org.alloy.metal.resource._Resource;
import org.alloy.metal.utilities._Exception;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

@Service
public class VaultResourceManager {
	private static final Logger logger = LogManager.getLogger(VaultResourceManager.class);

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private ModuleLoader moduleLoader;

	public Resource getResourceFromModule(Module module, String resourceLocation) {
		String resolvedLocation = module.getName() + "/" + resourceLocation;
		Resource resource = _Exception.ignore(() -> _Resource.getResource(resolvedLocation, applicationContext)).orElse(null);

		if (_Resource.exists(resource)) {
			return resource;
		}
		else {
			return null;
		}
	}

	public List<Resource> getResources(String baseLocation) {
		List<Resource> resources = Lists.newArrayList();

		for (Module module : Lists.reverse(moduleLoader.getModuleLoadOrder())) {
			String resolvedLocation = module.getName() + "/" + baseLocation;
			Resource resource = getResourceFromModule(module, baseLocation);

			if (resource != null) {
				logger.debug("Searching resource location " + resolvedLocation + " in " + module);
				resources.add(resource);
			}
		}

		logger.printf(Level.DEBUG, "Found resources [%s] for location [%s]", resources, baseLocation);
		return resources;
	}

	public Resource getResource(String resourceName) {
		return _Iterable.first(Iterables.filter(this.getResources(resourceName), _Resource::isValidResource));
	}

	public List<ClassPathResource> getConcreteResources(String baseLocation) {
		Iterable<List<Resource>> resources = _Iterable.transform(getResources(baseLocation), _Resource::getConcreteResources);
		return _List.list(_Iterable.transform(_Iterable.unique(_Iterable.flatten(resources), (first, second) -> visiblyEqual(baseLocation, first, second)), _Function.cast()));
	}

	public List<String> getConcreteVisibleResourcePaths(String baseLocation) {
		return _List.transform(this.getConcreteResources(baseLocation), (resource) -> convertToVisiblePath(baseLocation, resource.getPath()));
	}

	public boolean visiblyEqual(String baseLocation, Resource path1, Resource path2) {
		return visiblyEqual(baseLocation, _Resource.getPath(path1), _Resource.getPath(path2));
	}

	public boolean visiblyEqual(String baseLocation, String path1, String path2) {
		return convertToVisiblePath(baseLocation, path1).equals(convertToVisiblePath(baseLocation, path2));
	}

	public String convertToVisiblePath(String baseLocation, String path) {
		return path.substring(path.indexOf(baseLocation) + baseLocation.length());
	}
}