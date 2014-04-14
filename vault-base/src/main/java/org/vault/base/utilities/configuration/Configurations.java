package org.vault.base.utilities.configuration;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.vault.base.resources.stream.ResourceInputStream;

import com.google.common.base.Throwables;
import com.google.common.collect.Lists;

public class Configurations {
	public static ConfigurationLocation createClasspathLocation(String location) {
		ClasspathResourceConfigurationLocation configLocation = new ClasspathResourceConfigurationLocation();
		configLocation.setResourceLocation(location);
		return configLocation;
	}

	public static ConfigurationLocation createEnvironmentLocation(String string, String string2) {
		// TODO Auto-generated method stub
		return null;
	}

	private static List<ResourceInputStream> resolveResources(List<ConfigurationLocation> configurationLocations, ApplicationContext context) {
		List<ResourceInputStream> resources = Lists.newArrayList();
		for (ConfigurationLocation configurationLocation : configurationLocations) {
			resources.addAll(configurationLocation.resolveResources(context));
		}

		return resources;
	}

	public static List<ResourceInputStream> getConfigurations(List<ConfigurationLocation> configurationLocations, ApplicationContext applicationContext) {
		List<ResourceInputStream> configurations = Lists.newArrayList();
		for (ResourceInputStream resource : Configurations.resolveResources(configurationLocations, applicationContext)) {
			try {
				if (resource.available() <= 0) {
					resource.close();
					throw new IOException("Unable to open an input stream on specified application context resource: " + resource.getName());
				}
			} catch (IOException e) {
				throw Throwables.propagate(e);
			}

			configurations.add(resource);
		}

		return configurations;
	}

	public static List<ResourceInputStream> getConfigurations(ConfigurationLocation configurationLocation, ApplicationContext applicationContext) {
		return Configurations.getConfigurations(Collections.singletonList(configurationLocation), applicationContext);
	}
}
