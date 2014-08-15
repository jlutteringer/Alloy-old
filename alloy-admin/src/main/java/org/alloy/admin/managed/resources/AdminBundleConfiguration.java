package org.alloy.admin.managed.resources;

import java.util.List;

import org.alloy.site.resource.bundle.BundleConfigurationProvider;
import org.alloy.site.resource.bundle.configuration.BundleConfiguration;
import org.alloy.site.resource.bundle.configuration.BundleConfigurations;
import org.springframework.stereotype.Component;

@Component
public class AdminBundleConfiguration extends BundleConfigurationProvider {
	@Override
	protected void addConfigurations(List<BundleConfiguration> configurations) {
		configurations.add(
				BundleConfigurations.create("/css/admin-global-bundle.css")
						.dir("/css")
						.addResource("/global-bundle.css")
						.addResource("/libraries/bootstrap-darkly.css")
						.build());

		configurations.add(
				BundleConfigurations.create("/js/admin-global-bundle.js")
						.dir("/js")
						.addResource("/global-bundle.js")
						.addResource("/angular-bundle.js")
						.build());
	}
}
