package org.vault.base.utilities.configuration.classpath;

import org.springframework.context.ApplicationContext;
import org.vault.base.collections.directory.Directories;
import org.vault.base.collections.directory.Directory;
import org.vault.base.resources.stream.ResourceInputStream;
import org.vault.base.utilities.configuration.Configurations;

public class BaseClasspathResourceConfigurationLocation extends AbstractClasspathResourceConfigurationLocation {
	private String resourceLocation;

	public void setResourceLocation(String resourceLocation) {
		this.resourceLocation = resourceLocation;
	}

	@Override
	public String getResourceLocation(String key) {
		return resourceLocation;
	}

	@Override
	public Directory<String, ResourceInputStream> resolveResources(ApplicationContext context) {
		return Directories.newUnkeyedDirectory(Configurations.resolveClasspathResource(this.getResourceLocation(null), context));
	}
}