package org.vault.extensibility.context;

import java.util.List;

import org.springframework.context.ConfigurableApplicationContext;
import org.vault.base.utilities.configuration.ConfigurationLocation;

public interface MergeApplicationContext extends ConfigurableApplicationContext {
	public List<ConfigurationLocation> getPatchLocations();

	public void setPatchLocations(List<ConfigurationLocation> configurationLocations);
}