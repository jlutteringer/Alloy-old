package org.vault.angular.managed.resources;

import java.util.List;

import org.springframework.stereotype.Component;
import org.vault.site.resource.bundle.BundleConfigurationProvider;
import org.vault.site.resource.bundle.configuration.BundleConfiguration;
import org.vault.site.resource.bundle.configuration.BundleConfigurations;

@Component
public class AngularCoreBundleConfiguration extends BundleConfigurationProvider {
	@Override
	protected void addConfigurations(List<BundleConfiguration> configurations) {
		configurations.add(
				BundleConfigurations.create("/js/angular-core.js")
						.dir("/js")
						.addPath("/angular-core/**")
						.build());
	}
}