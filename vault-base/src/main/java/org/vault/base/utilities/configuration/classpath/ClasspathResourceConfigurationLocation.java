package org.vault.base.utilities.configuration.classpath;

import org.vault.base.utilities.configuration.ConfigurationLocation;

public interface ClasspathResourceConfigurationLocation extends ConfigurationLocation {
	public String getResourceLocation(String key);
}