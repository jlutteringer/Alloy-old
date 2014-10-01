package org.alloy.angular.managed.resources;

import java.util.List;

import org.alloy.site.resource.ResourceConfigurationProvider;
import org.alloy.site.resource.configuration.ResourceConfiguration;
import org.alloy.site.resource.configuration.ResourceConfigurations;
import org.springframework.stereotype.Component;

@Component
public class AngularCoreBundleConfiguration extends ResourceConfigurationProvider {
	@Override
	protected void addConfigurations(List<ResourceConfiguration> configurations) {
		configurations.add(ResourceConfigurations.alias("/js/angular-bundle.js")
				.include("/js/libraries/angular/angular.js")
				.include("/js/libraries/angular/ui-router.js")
				.include("/js/angular/**")
				.build());
	}
}