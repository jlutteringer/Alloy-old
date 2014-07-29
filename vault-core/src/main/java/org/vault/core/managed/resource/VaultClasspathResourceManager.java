package org.vault.core.managed.resource;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.vault.base.collections.iterable.VIterables;
import org.vault.base.module.domain.Module;
import org.vault.base.module.service.ModuleLoader;
import org.vault.base.resource.VResources;
import org.vault.base.utilities.configuration.Configurations;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

@Service
public class VaultClasspathResourceManager {
	private static final Logger logger = LogManager.getLogger(VaultClasspathResourceManager.class);

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private ModuleLoader moduleLoader;

	public Resource getResourceFromModule(Module module, String resourceLocation) {
		String resolvedLocation = module.getName() + "/" + resourceLocation;
		Resource resource = null;
		try {
			resource = Configurations.resolveClasspathResource(resolvedLocation, applicationContext);
		} catch (Exception e) {
			// Eat it
		}

		return resource;
	}

	public List<Resource> getLocations(String baseLocation) {
		List<Resource> resources = Lists.newArrayList();

		for (Module module : Lists.reverse(moduleLoader.getModuleLoadOrder())) {
			String resolvedLocation = module.getName() + "/" + baseLocation;
			Resource resource = getResourceFromModule(module, baseLocation);

			if (resource != null) {
				logger.debug("Searching resource location " + resolvedLocation + " in " + module);
				resources.add(resource);
			}
		}

		return resources;
	}

	public Resource getResource(String resourceName) {
		return VIterables.first(Iterables.filter(this.getLocations(resourceName), VResources::isValidResource));
	}
}
