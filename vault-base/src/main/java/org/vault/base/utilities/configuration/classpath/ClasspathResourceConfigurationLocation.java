package org.vault.base.utilities.configuration.classpath;

import java.util.List;

import org.vault.base.utilities.configuration.ConfigurationLocation;

public interface ClasspathResourceConfigurationLocation extends ConfigurationLocation {
	public String getResourceLocation(String key);

	public List<String> getKeys();
}