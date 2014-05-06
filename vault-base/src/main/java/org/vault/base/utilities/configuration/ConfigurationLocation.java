package org.vault.base.utilities.configuration;

import org.springframework.context.ApplicationContext;
import org.vault.base.collections.directory.Directory;
import org.vault.base.resources.stream.ResourceInputStream;

public interface ConfigurationLocation {
	public Directory<String, ResourceInputStream> resolveResources(ApplicationContext context);
}