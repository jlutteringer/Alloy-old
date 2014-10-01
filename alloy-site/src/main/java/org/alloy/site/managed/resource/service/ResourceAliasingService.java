package org.alloy.site.managed.resource.service;

import java.util.Collection;

import org.alloy.metal.collections.iterable._Iterable;
import org.alloy.metal.domain.Path;
import org.alloy.metal.spring.AlloyBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;

@Service
public class ResourceAliasingService extends AlloyBean {
	private Multimap<String, String> aliases = LinkedHashMultimap.create();

	@Autowired
	private AlloyResourceResolverService resourceResolver;

	@Autowired
	private ResourceBundlingService resourceBundlingService;

	public boolean isAlias(Path path) {
		return aliases.containsKey(path.getPath());
	}

	public void register(String aliasPath, Iterable<String> resourceLocations) {
		logger.debug("Registering alias: {} with locations: {}", aliasPath, resourceLocations);
		aliases.putAll(aliasPath, resourceLocations);
	}

	public Resource resolve(String aliasPath) {
		Collection<String> resourceLocations = aliases.get(aliasPath);
		if (resourceLocations.size() == 1) {
			return resourceResolver.getResource(Path.of(_Iterable.getSingleResult(resourceLocations)));
		}

		Iterable<Resource> resources = _Iterable.transform(aliases.get(aliasPath), (resourceLocation) -> resourceResolver.getResource(Path.of(resourceLocation)));
		return resourceBundlingService.bundle(aliasPath, resources);
	}
}
