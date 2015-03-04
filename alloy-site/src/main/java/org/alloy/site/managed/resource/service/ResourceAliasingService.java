package org.alloy.site.managed.resource.service;

import org.alloy.metal.collections.ACollection;
import org.alloy.metal.collections.map.MutableMultimap;
import org.alloy.metal.collections.map._Maps;
import org.alloy.metal.domain.Path;
import org.alloy.metal.iteration._Iteration;
import org.alloy.metal.spring.AlloyBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.google.common.collect.LinkedHashMultimap;

@Service
public class ResourceAliasingService extends AlloyBean {
	private MutableMultimap<String, String> aliases = _Maps.wrap(LinkedHashMultimap.create());

	@Autowired
	private AlloyResourceResolverService resourceResolver;

	@Autowired
	private ResourceBundlingService resourceBundlingService;

	public boolean isAlias(Path path) {
		return aliases.containsKey(path.getPath());
	}

	public void register(String aliasPath, Iterable<String> resourceLocations) {
		logger.debug("Registering alias: {} with locations: {}", aliasPath, resourceLocations);
		aliases.putAll(aliasPath, _Iteration.cursor(resourceLocations));
	}

	public Resource resolve(String aliasPath) {
		ACollection<String> resourceLocations = aliases.get(aliasPath);
		if (resourceLocations.getSizeInfo().getFixedSize() == 1) {
			return resourceResolver.getResource(Path.of(resourceLocations.singleStrict()));
		}

		return resourceBundlingService.bundle(aliasPath,
				resourceLocations.map((resourceLocation) -> resourceResolver.getResource(Path.of(resourceLocation))));
	}
}
