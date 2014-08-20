package org.alloy.web.managed.resources;

import java.util.List;

import org.alloy.site.resource.bundle.ResourceConfigurationProvider;
import org.alloy.site.resource.bundle.configuration.ResourceConfiguration;
import org.alloy.site.resource.bundle.configuration.ResourceConfigurations;
import org.springframework.stereotype.Component;

@Component
public class WebBundleConfiguration extends ResourceConfigurationProvider {
	@Override
	protected void addConfigurations(List<ResourceConfiguration> configurations) {
		configurations.add(
				ResourceConfigurations.create("/css/global-bundle.css")
						.dir("/css")
						.addResource("/libraries/bootstrap.css")
						.addResource("/libraries/font-awesome/font-awesome.css")
						.addResource("/global.css")
						.build());

		configurations.add(
				ResourceConfigurations.create("/js/global-bundle.js")
						.dir("/js")
						.addResource("/libraries/jquery.js")
						.addResource("/libraries/bootstrap.js")
						.addResource("/libraries/underscore.js")
						.addResource("/libraries/underscore-string.js")
						.build());
	}
}
