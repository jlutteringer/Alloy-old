package org.vault.base.utilities.configuration.classpath;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.vault.base.collections.directory.Directories;
import org.vault.base.collections.directory.Directory;
import org.vault.base.resources.stream.ResourceInputStream;
import org.vault.base.utilities.configuration.AbstractConfigurationLocation;
import org.vault.base.utilities.configuration.Configurations;

public abstract class AbstractClasspathResourceConfigurationLocation extends AbstractConfigurationLocation implements ClasspathResourceConfigurationLocation {
	@Override
	public String toString() {
		return this.getResourceLocation(null);
	}

	@Override
	public Directory<String, ResourceInputStream> resolveResources(ApplicationContext context) {
		Directory<String, ResourceInputStream> directory = Directories.newDirectory();

		List<String> keys = this.getKeys();
		if (keys.isEmpty()) {
			directory.put(null, Configurations.resolveClasspathResource(this.getResourceLocation(null), context));
		}
		for (String key : keys) {
			directory.put(key, Configurations.resolveClasspathResource(this.getResourceLocation(key), context));
		}

		return directory;
	}
}
