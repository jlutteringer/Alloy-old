package org.alloy.site.managed.resource.service.configuration;

import java.util.List;

import javax.annotation.PostConstruct;

import org.alloy.core.managed.resource.AlloyResourceManager;
import org.alloy.metal.ant._Ant;
import org.alloy.metal.collections.iterable._Iterable;
import org.alloy.metal.extensibility.TypeFilteringConfigurationResolver;
import org.alloy.site.managed.resource.service.ResourceAliasingService;
import org.alloy.site.resource.configuration.ResourceAliasConfiguration;
import org.alloy.site.resource.configuration.ResourceConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ResourceAliasConfigurationResolver extends TypeFilteringConfigurationResolver<ResourceConfiguration, ResourceAliasConfiguration> {
	@Autowired
	private ResourceAliasingService resourceAliasingService;

	@Autowired
	protected AlloyResourceManager resourceManager;

	@PostConstruct
	public void initialize() {
		List<ResourceAliasConfiguration> aliases = this.getResolvedItems();
		Iterable<String> resourcePaths =
				_Iterable.concat(resourceManager.getConcreteVisibleResourcePaths("resources"), _Iterable.transform(aliases, (alias) -> alias.getAliasPath()));

		logger.debug("For concrete resource paths: " + resourcePaths);

		for (ResourceAliasConfiguration configuration : aliases) {
			Iterable<String> matchingPaths = _Iterable.empty();
			for (String resourceLocation : configuration.getResourceLocations()) {
				matchingPaths = _Iterable.concat(matchingPaths, _Iterable.filter(resourcePaths, _Ant.pathMatcher(resourceLocation)));
			}

			resourceAliasingService.register(configuration.getAliasPath(), matchingPaths);
		}
	}
}