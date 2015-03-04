package org.alloy.site.managed.resource.service.configuration;

import javax.annotation.PostConstruct;

import org.alloy.core.managed.resource.AlloyResourceManager;
import org.alloy.metal.ant._Ant;
import org.alloy.metal.collections.list.AList;
import org.alloy.metal.collections.list.MutableList;
import org.alloy.metal.collections.list._Lists;
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
		AList<ResourceAliasConfiguration> aliases = _Lists.wrap(this.getResolvedItems());

		AList<String> resourcePaths = _Lists.wrap(resourceManager.getConcreteVisibleResourcePaths("resources"))
				.merge(aliases.map((alias) -> alias.getAliasPath()))
				.collectList();

		logger.debug("For concrete resource paths: " + resourcePaths);

		for (ResourceAliasConfiguration configuration : aliases) {
			MutableList<String> matchingPaths = _Lists.list();
			for (String resourceLocation : configuration.getResourceLocations()) {
				matchingPaths.addAll(resourcePaths.filter(_Ant.pathMatcher(resourceLocation)));
			}

			resourceAliasingService.register(configuration.getAliasPath(), matchingPaths);
		}
	}
}