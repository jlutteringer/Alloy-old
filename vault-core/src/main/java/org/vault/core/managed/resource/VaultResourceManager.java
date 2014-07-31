package org.vault.core.managed.resource;

import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.vault.base.collections.iterable.VIterables;
import org.vault.base.collections.lists.VLists;
import org.vault.base.module.domain.Module;
import org.vault.base.module.service.ModuleLoader;
import org.vault.base.resource.VResources;
import org.vault.base.utilities.exception.Exceptions;
import org.vault.base.utilities.function.VFunctions;

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
		Resource resource = Exceptions.ignore(() -> VResources.getResource(resolvedLocation, applicationContext)).orElse(null);

		if (VResources.exists(resource)) {
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
		return VIterables.first(Iterables.filter(this.getResources(resourceName), VResources::isValidResource));
	}

	public List<ClassPathResource> getConcreteResources(String baseLocation) {
		Iterable<List<Resource>> resources = VIterables.transform(getResources(baseLocation), VResources::getConcreteResources);
		return VLists.list(VIterables.transform(VIterables.unique(VIterables.flatten(resources), (first, second) -> visiblyEqual(baseLocation, first, second)), VFunctions.cast()));
	}

	public List<String> getConcreteVisibleResourcePaths(String baseLocation) {
		return VLists.transform(this.getConcreteResources(baseLocation), (resource) -> convertToVisiblePath(baseLocation, resource.getPath()));
	}

	public boolean visiblyEqual(String baseLocation, Resource path1, Resource path2) {
		return visiblyEqual(baseLocation, VResources.getPath(path1), VResources.getPath(path2));
	}

	public boolean visiblyEqual(String baseLocation, String path1, String path2) {
		return convertToVisiblePath(baseLocation, path1).equals(convertToVisiblePath(baseLocation, path2));
	}

	public String convertToVisiblePath(String baseLocation, String path) {
		return path.substring(path.indexOf(baseLocation) + baseLocation.length());
	}
}