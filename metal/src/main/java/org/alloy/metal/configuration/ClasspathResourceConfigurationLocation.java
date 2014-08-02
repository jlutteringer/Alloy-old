package org.alloy.metal.configuration;

import java.util.List;

import org.alloy.metal.collections.directory.Directory;
import org.alloy.metal.resource.ResourceInputStream;
import org.springframework.context.ApplicationContext;

public interface ClasspathResourceConfigurationLocation extends ConfigurationLocation {
	public Directory<String, String> getResourceLocationDirectory();

	public List<ResourceInputStream> resolveResouceLocations(String resourceLocation, ApplicationContext context);
}