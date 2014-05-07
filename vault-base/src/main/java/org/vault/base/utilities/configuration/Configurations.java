package org.vault.base.utilities.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.vault.base.collections.directory.Directories;
import org.vault.base.collections.directory.Directory;
import org.vault.base.enviornment.EnvironmentType;
import org.vault.base.module.domain.Module;
import org.vault.base.resources.stream.ResourceInputStream;
import org.vault.base.utilities.configuration.classpath.BaseClasspathResourceConfigurationLocation;
import org.vault.base.utilities.configuration.classpath.ClasspathResourceConfigurationLocation;
import org.vault.base.utilities.configuration.classpath.EnvironmentCRCLDecorator;
import org.vault.base.utilities.configuration.classpath.ModuleRelativeCRCLDecorator;

import com.google.common.base.Throwables;
import com.google.common.collect.Lists;

public class Configurations {
	public static ClasspathResourceConfigurationLocation moduleRelative(ClasspathResourceConfigurationLocation location, Module module) {
		return new ModuleRelativeCRCLDecorator(location, module);
	}

	public static ClasspathResourceConfigurationLocation createClasspathLocation(String location) {
		BaseClasspathResourceConfigurationLocation configLocation = new BaseClasspathResourceConfigurationLocation();
		configLocation.setResourceLocation(location);
		return configLocation;
	}

	public static ClasspathResourceConfigurationLocation createEnvironmentLocation(String locationTemplate, EnvironmentType currentEnvironment) {
		EnvironmentCRCLDecorator configLocation = new EnvironmentCRCLDecorator(createClasspathLocation(locationTemplate));
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

	public static ResourceInputStream resolveClasspathResource(String resourceLocation, ApplicationContext context) {
		ResourceInputStream stream;
		if (resourceLocation.startsWith("classpath")) {
			InputStream is = ClasspathResourceConfigurationLocation.class.getClassLoader()
					.getResourceAsStream(resourceLocation.substring("classpath*:".length(), resourceLocation.length()));

			stream = new ResourceInputStream(is, resourceLocation);
		} else {
			try {
				stream = ResourceInputStream.create(resourceLocation, context);
			} catch (IOException e) {
				throw Throwables.propagate(e);
			}
		}

		return stream;
	}
}
