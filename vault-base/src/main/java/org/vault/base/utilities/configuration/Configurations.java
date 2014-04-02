package org.vault.base.utilities.configuration;

import java.util.List;

import com.google.common.collect.Lists;

public class Configurations {
	public static ConfigurationLocation createLocation(String location) {
		ConfigurationLocation configLocation = new ConfigurationLocation();
		configLocation.setLocation(location);
		return configLocation;
	}

	public static List<String> parseLocations(List<ConfigurationLocation> configurationLocations) {
		List<String> parsedLocations = Lists.newArrayList();
		for (ConfigurationLocation configurationLocation : configurationLocations) {
			parsedLocations.add(configurationLocation.getLocation());
		}

		return parsedLocations;
	}
}
