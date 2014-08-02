package org.alloy.forge.module;

import java.util.List;

import org.alloy.metal.configuration.ConfigurationLocation;

public interface PrimaryModuleFacet extends ModuleFacet {
	public List<ConfigurationLocation> getConfigurationLocations();

	public List<ConfigurationLocation> getPropertyConfigurationLocations();

	public List<ConfigurationLocation> getLog4jConfigurationLocations();
}