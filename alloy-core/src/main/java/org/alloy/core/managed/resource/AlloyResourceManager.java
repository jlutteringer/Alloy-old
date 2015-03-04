package org.alloy.core.managed.resource;

import java.util.List;
import java.util.Optional;

import org.alloy.forge.module.Module;
import org.alloy.forge.module.ModuleLoader;
import org.alloy.forge.module.ModuleType;
import org.alloy.metal.collections.list._Lists;
import org.alloy.metal.resource._Resource;
import org.alloy.metal.spring._ApplicationResource;
import org.alloy.metal.utility._Exception;
import org.alloy.metal.utility._Predicate;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

@Service
public class AlloyResourceManager {
	private static final Logger logger = LogManager.getLogger(AlloyResourceManager.class);

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private ModuleLoader moduleLoader;

	public Resource getResourceFromModule(Module module, String resourceLocation) {
		String resolvedLocation;
		if (module.getType().equals(ModuleType.APPLICATION)) {
			resolvedLocation = resourceLocation;
		}
		else {
			resolvedLocation = module.getName() + "/" + resourceLocation;
		}

		Resource resource = _Exception.ignore(() -> _ApplicationResource.getResource(resolvedLocation, applicationContext)).orElse(null);

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
				logger.trace("Searching resource location " + resolvedLocation + " in " + module);
				resources.add(resource);
			}
		}

		logger.printf(Level.TRACE, "Found resources [%s] for location [%s]", resources, baseLocation);
		return resources;
	}

	public Optional<Resource> getResource(String resourceName) {
		return _Lists.wrap(this.getResources(resourceName))
				.filter(_Resource::isValidResource)
				.first();
	}

	public List<ClassPathResource> getConcreteResources(String baseLocation) {
		return _Lists.wrap(getResources(baseLocation))
				.map(_ApplicationResource::getConcreteResources)
				.flatMap((resources) -> _Lists.wrap(resources))
				.filter(_Predicate.matchSeen((first, second) -> visiblyEqual(baseLocation, first, second)))
				.map(resource -> (ClassPathResource) resource)
				.collectList()
				.asList();
	}

	public List<String> getConcreteVisibleResourcePaths(String baseLocation) {
		return _Lists.wrap(this.getConcreteResources(baseLocation))
				.map((resource) -> convertToVisiblePath(baseLocation, resource.getPath()))
				.collectList()
				.asList();
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