package org.vault.base.utilities.configuration;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.vault.base.collections.directory.Directories;
import org.vault.base.collections.directory.Directory;
import org.vault.base.collections.iterable.VIterables;
import org.vault.base.enviornment.EnvironmentType;
import org.vault.base.module.domain.Module;
import org.vault.base.resource.ResourceInputStream;
import org.vault.base.utilities.configuration.classpath.ClasspathResourceConfigurationLocation;
import org.vault.base.utilities.configuration.classpath.EnvironmentClasspathResouceConfigurationLocation;
import org.vault.base.utilities.configuration.classpath.ModuleRelativeCRCLDecorator;
import org.vault.base.utilities.configuration.classpath.OptionalCRCLDecorator;
import org.vault.base.utilities.configuration.classpath.SingletonClasspathResourceConfigurationLocation;

import com.google.common.base.Throwables;
import com.google.common.collect.Lists;

public class Configurations {
	private static final Logger logger = LogManager.getLogger(Configurations.class);

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
		Directory<String, ResourceInputStream> directory = Directories.newDirectory();
		for (ConfigurationLocation configurationLocation : configurationLocations) {
			Directory<String, ResourceInputStream> subDirectory = configurationLocation.resolveResources(context);
			directory.putAll(subDirectory);
		}

		return directory.get();
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

	public static Resource resolveClasspathResource(String resourceLocation, ApplicationContext context) {
		return VIterables.getSingleResult(resolveClasspathResources(resourceLocation, context));
	}

	public static List<Resource> resolveClasspathResources(String resourceLocation, ApplicationContext context) {
		logger.trace("Resolving classpath resource at: [" + resourceLocation + "]");
		try {
			List<Resource> resources = Lists.newArrayList(context.getResources(resourceLocation));
			return resources;
		} catch (IOException e) {
			throw Throwables.propagate(e);
		}
	}
}
