package org.vault.module.registry.core;

import java.util.List;

import org.vault.base.module.domain.ModuleFacet;
import org.vault.base.utilities.configuration.ConfigurationLocation;

import com.google.common.collect.Lists;

public class CoreFacet implements ModuleFacet {
	protected List<String> dependencies = Lists.newArrayList();
	protected List<ConfigurationLocation> configurationLocations = Lists.newArrayList();
	protected List<ConfigurationLocation> log4jConfigurationLocations = Lists.newArrayList();
	protected List<ConfigurationLocation> propertyConfigurationLocations = Lists.newArrayList();

	public List<ConfigurationLocation> getConfigurationLocations() {
		return configurationLocations;
	}

	public List<ConfigurationLocation> getPropertyConfigurationLocations() {
		return propertyConfigurationLocations;
	}

	public List<ConfigurationLocation> getLog4jConfigurationLocations() {
		return log4jConfigurationLocations;
	}
}
