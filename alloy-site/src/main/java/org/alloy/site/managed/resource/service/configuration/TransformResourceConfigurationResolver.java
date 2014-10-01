package org.alloy.site.managed.resource.service.configuration;

import javax.annotation.PostConstruct;

import org.alloy.metal.extensibility.TypeFilteringConfigurationResolver;
import org.alloy.site.managed.resource.handler.InjectPropertiesResourceTransformer;
import org.alloy.site.resource.configuration.ResourceConfiguration;
import org.alloy.site.resource.configuration.TransformResourceConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransformResourceConfigurationResolver extends TypeFilteringConfigurationResolver<ResourceConfiguration, TransformResourceConfiguration> {
	@Autowired
	private InjectPropertiesResourceTransformer injectPropertiesResourceTransformer;

	@PostConstruct
	private void initalize() {
		for (TransformResourceConfiguration configuration : this.getResolvedItems()) {
			if (configuration.isInjectProperties()) {
				configuration.getResourceLocations().forEach(injectPropertiesResourceTransformer::register);
			}
		}
	}
}