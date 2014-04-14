package org.vault.base.utilities.configuration;

import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.vault.base.resources.stream.ResourceInputStream;

public interface ConfigurationLocation {
	public Map<String, ResourceInputStream> resolveResources(ApplicationContext context);
}