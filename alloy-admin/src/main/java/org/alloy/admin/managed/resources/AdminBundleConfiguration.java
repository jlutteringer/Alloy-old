package org.alloy.admin.managed.resources;

import java.util.List;

import org.alloy.site.resource.bundle.ResourceConfigurationProvider;
import org.alloy.site.resource.bundle.configuration.ResourceConfiguration;
import org.alloy.site.resource.bundle.configuration.ResourceConfigurations;
import org.springframework.stereotype.Component;

@Component
public class AdminBundleConfiguration extends ResourceConfigurationProvider {
	@Override
	protected void addConfigurations(List<ResourceConfiguration> configurations) {
		configurations.add(
				ResourceConfigurations.create("/css/admin-global-bundle.css")
						.dir("/css")
						.addResource("/global-bundle.css")
						.addResource("/libraries/bootstrap-darkly.css")
						.build());

		configurations.add(
				ResourceConfigurations.create("/js/admin-global-bundle.js")
						.dir("/js")
						.addResource("/global-bundle.js")
						.addResource("/angular-bundle.js")
						.build());
	}
}