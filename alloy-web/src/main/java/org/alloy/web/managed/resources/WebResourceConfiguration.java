package org.alloy.web.managed.resources;

import java.util.List;

import org.alloy.site.resource.ResourceConfigurationProvider;
import org.alloy.site.resource.configuration.ResourceConfiguration;
import org.alloy.site.resource.configuration.ResourceConfigurations;
import org.springframework.stereotype.Component;

@Component
public class WebResourceConfiguration extends ResourceConfigurationProvider {
	@Override
	protected void addConfigurations(List<ResourceConfiguration> configurations) {
		configurations.add(ResourceConfigurations.alias("/css/global-bundle.css")
				.include("/css/libraries/bootstrap.css")
				.include("/css/libraries/font-awesome/font-awesome.css")
				.include("/css/global.less")
				.build());

		configurations.add(ResourceConfigurations.alias("/js/global-bundle.js")
				.include("/js/libraries/require.js")
				.include("/js/libraries/bootstrap.js")
				.include("/js/libraries/underscore.js")
				.include("/js/libraries/underscore-string.js")
				.build());

		configurations.add(ResourceConfigurations.injectProperties("/css/libraries/font-awesome/font-awesome.css"));
	}
}
