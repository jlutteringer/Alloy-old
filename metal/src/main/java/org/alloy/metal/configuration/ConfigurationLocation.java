package org.alloy.metal.configuration;

import org.alloy.metal.collections.directory.Directory;
import org.alloy.metal.resource.ResourceInputStream;
import org.springframework.context.ApplicationContext;

public interface ConfigurationLocation {
	public Directory<String, ResourceInputStream> resolveResources(ApplicationContext context);
}