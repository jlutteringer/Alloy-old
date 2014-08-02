package org.alloy.metal.configuration;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.alloy.forge.module.Module;
import org.alloy.metal.collections.directory.Directory;
import org.alloy.metal.collections.directory._Directory;
import org.alloy.metal.environment.EnvironmentType;
import org.alloy.metal.resource.ResourceInputStream;
import org.springframework.context.ApplicationContext;

import com.google.common.base.Throwables;
import com.google.common.collect.Lists;

public class _Configuration {
	public static ClasspathResourceConfigurationLocation moduleRelative(ClasspathResourceConfigurationLocation location, Module module) {
		return new ModuleRelativeCRCLDecorator(location, module);
	}

	public static ClasspathResourceConfigurationLocation optional(ClasspathResourceConfigurationLocation location) {
		return new OptionalCRCLDecorator(location);
	}

	public static ClasspathResourceConfigurationLocation createClasspathLocation(String location) {
		SingletonClasspathResourceConfigurationLocation configLocation = new SingletonClasspathResourceConfigurationLocation();
		configLocation.setResourceLocation(location);
		return configLocation;
	}

	public static ClasspathResourceConfigurationLocation createEnvironmentLocation(String locationTemplate, EnvironmentType currentEnvironment) {
		EnvironmentClasspathResouceConfigurationLocation configLocation = new EnvironmentClasspathResouceConfigurationLocation();
		configLocation.setResourceLocation(locationTemplate);
		configLocation.setCurrentEnvironment(currentEnvironment);
		return configLocation;
	}

	private static List<ResourceInputStream> resolveResources(List<ConfigurationLocation> configurationLocations, ApplicationContext context) {
		Directory<String, ResourceInputStream> directory = _Directory.newDirectory();
		for (ConfigurationLocation configurationLocation : configurationLocations) {
			Directory<String, ResourceInputStream> subDirectory = configurationLocation.resolveResources(context);
			directory.putAll(subDirectory);
		}

		return directory.get();
	}

	public static List<ResourceInputStream> getConfigurations(List<ConfigurationLocation> configurationLocations, ApplicationContext applicationContext) {
		List<ResourceInputStream> configurations = Lists.newArrayList();
		for (ResourceInputStream resource : _Configuration.resolveResources(configurationLocations, applicationContext)) {
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
		return _Configuration.getConfigurations(Collections.singletonList(configurationLocation), applicationContext);
	}
}
