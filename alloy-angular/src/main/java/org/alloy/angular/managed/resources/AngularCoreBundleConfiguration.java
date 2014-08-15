package org.alloy.angular.managed.resources;

import java.util.List;

import org.alloy.site.resource.bundle.BundleConfigurationProvider;
import org.alloy.site.resource.bundle.configuration.BundleConfiguration;
import org.alloy.site.resource.bundle.configuration.BundleConfigurations;
import org.springframework.stereotype.Component;

@Component
public class AngularCoreBundleConfiguration extends BundleConfigurationProvider {
	@Override
	protected void addConfigurations(List<BundleConfiguration> configurations) {
		configurations.add(
				BundleConfigurations.create("/js/angular-bundle.js")
						.dir("/js")
						.addResource("/libraries/angular/angular.js")
						.addResource("/libraries/angular/ui-router.js")
						.addPath("/angular/**")
						.build());
	}
}