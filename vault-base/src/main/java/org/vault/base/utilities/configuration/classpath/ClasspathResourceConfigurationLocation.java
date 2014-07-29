package org.vault.base.utilities.configuration.classpath;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.vault.base.collections.directory.Directory;
import org.vault.base.resource.ResourceInputStream;
import org.vault.base.utilities.configuration.ConfigurationLocation;

public interface ClasspathResourceConfigurationLocation extends ConfigurationLocation {
	public Directory<String, String> getResourceLocationDirectory();

	public List<ResourceInputStream> resolveResouceLocations(String resourceLocation, ApplicationContext context);
}