package org.alloy.admin.managed.resources;

import java.util.List;

import org.alloy.site.resource.ResourceConfigurationProvider;
import org.alloy.site.resource.configuration.ResourceConfiguration;
import org.alloy.site.resource.configuration.ResourceConfigurations;
import org.springframework.stereotype.Component;

@Component
public class AdminResourceConfiguration extends ResourceConfigurationProvider {
	@Override
	protected void addConfigurations(List<ResourceConfiguration> configurations) {
		configurations.add(ResourceConfigurations.alias("/css/admin-global-bundle.css")
				.include("/css/global-bundle.css")
				.include("/css/libraries/bootstrap-darkly.css")
				.build());

		configurations.add(ResourceConfigurations.alias("/js/admin-global-bundle.js")
				.include("/js/global-bundle.js")
				.include("/js/angular-bundle.js")
				.build());
	}
}