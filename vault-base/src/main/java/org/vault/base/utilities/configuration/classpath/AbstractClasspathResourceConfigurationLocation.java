package org.vault.base.utilities.configuration.classpath;

import java.util.function.Function;

import org.springframework.context.ApplicationContext;
import org.vault.base.collections.directory.Directories;
import org.vault.base.collections.directory.Directory;
import org.vault.base.resources.stream.ResourceInputStream;
import org.vault.base.utilities.configuration.AbstractConfigurationLocation;
import org.vault.base.utilities.configuration.Configurations;

public abstract class AbstractClasspathResourceConfigurationLocation extends AbstractConfigurationLocation implements ClasspathResourceConfigurationLocation {
	protected Directory<String, ResourceInputStream> resolveResourcesIternal(ApplicationContext context, Function<String, String> getResourceLocation) {
		return Directories.newUnkeyedDirectory(Configurations.resolveClasspathResource(getResourceLocation.apply(null), context));
	}

	@Override
	public Directory<String, ResourceInputStream> resolveResources(ApplicationContext context) {
		return resolveResourcesIternal(context, this::getResourceLocation);
	}
}
