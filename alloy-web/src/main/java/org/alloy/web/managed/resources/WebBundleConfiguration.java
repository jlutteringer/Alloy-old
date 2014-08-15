package org.alloy.web.managed.resources;

import java.util.List;

import org.alloy.site.resource.bundle.BundleConfigurationProvider;
import org.alloy.site.resource.bundle.configuration.BundleConfiguration;
import org.alloy.site.resource.bundle.configuration.BundleConfigurations;
import org.springframework.stereotype.Component;

@Component
public class WebBundleConfiguration extends BundleConfigurationProvider {
	@Override
	protected void addConfigurations(List<BundleConfiguration> configurations) {
		configurations.add(
				BundleConfigurations.create("/css/global-bundle.css")
						.dir("/css")
						.addResource("/libraries/bootstrap.css")
						.addResource("/libraries/font-awesome/font-awesome.css")
						.addResource("/global.css")
						.build());

		configurations.add(
				BundleConfigurations.create("/js/global-bundle.js")
						.dir("/js")
						.addResource("/libraries/jquery.js")
						.addResource("/libraries/bootstrap.js")
						.addResource("/libraries/underscore.js")
						.addResource("/libraries/underscore-string.js")
						.build());
	}
}
