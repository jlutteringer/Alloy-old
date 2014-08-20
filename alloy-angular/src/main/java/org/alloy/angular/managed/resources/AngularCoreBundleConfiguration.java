package org.alloy.angular.managed.resources;

import java.util.List;

import org.alloy.site.resource.bundle.ResourceConfigurationProvider;
import org.alloy.site.resource.bundle.configuration.ResourceConfiguration;
import org.alloy.site.resource.bundle.configuration.ResourceConfigurations;
import org.springframework.stereotype.Component;

@Component
public class AngularCoreBundleConfiguration extends ResourceConfigurationProvider {
	@Override
	protected void addConfigurations(List<ResourceConfiguration> configurations) {
		configurations.add(
				ResourceConfigurations.create("/js/angular-bundle.js")
						.dir("/js")
						.addResource("/libraries/angular/angular.js")
						.addResource("/libraries/angular/ui-router.js")
						.addPath("/angular/**")
						.build());
	}
}