package org.vault.core.managed.resource;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.vault.base.collections.iterable.VIterables;
import org.vault.base.collections.tree.Trees;
import org.vault.base.module.domain.Module;
import org.vault.base.module.service.ModuleLoader;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

@Service
public class VaultClasspathResourceManager {
	private static final Logger logger = LogManager.getLogger(VaultClasspathResourceManager.class);

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private ModuleLoader moduleLoader;

	public List<Resource> getLocations(String baseLocation) {
		List<Resource> resources = Lists.newArrayList();

		for (Module module : VIterables.reverse(Trees.iterateDepthFirst(moduleLoader.getModuleHierarchy().getModules()))) {
			String resolvedLocation = module.getName() + "/" + baseLocation;
			Resource resource = null;
			try {
				resource = new UrlResource(applicationContext.getClassLoader().getResource(resolvedLocation));
			} catch (Exception e) {
				// Eat it
			}

			if (resource != null) {
				logger.debug("Found resource location " + resolvedLocation + " in " + module);
				resources.add(resource);
			}
		}

		return resources;
	}

	public Resource getResource(String resourceName) {
		return Iterables.getFirst(this.getLocations(resourceName), null);
	}
}
