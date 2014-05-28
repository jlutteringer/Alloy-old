package org.vault.base.module.domain;

import java.util.List;

import org.vault.base.utilities.configuration.ConfigurationLocation;

public interface PrimaryFacet extends ModuleFacet {
	public List<ConfigurationLocation> getConfigurationLocations();

	public List<ConfigurationLocation> getPropertyConfigurationLocations();

	public List<ConfigurationLocation> getLog4jConfigurationLocations();
}
