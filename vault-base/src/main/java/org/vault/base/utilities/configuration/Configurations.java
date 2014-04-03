package org.vault.base.utilities.configuration;

import java.io.IOException;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.vault.base.resources.stream.ResourceInputStream;

import com.google.common.collect.Lists;

public class Configurations {
	public static ConfigurationLocation createClasspathLocation(String location) {
		ClasspathResourceConfigurationLocation configLocation = new ClasspathResourceConfigurationLocation();
		configLocation.setResourceLocation(location);
		return configLocation;
	}

	public static List<ResourceInputStream> resolveResources(List<ConfigurationLocation> configurationLocations, ApplicationContext context) throws IOException {
		List<ResourceInputStream> resources = Lists.newArrayList();
		for (ConfigurationLocation configurationLocation : configurationLocations) {
			resources.add(configurationLocation.resolveResource(context));
		}

		return resources;
	}
}
