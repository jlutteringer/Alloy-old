package org.alloy.forge.module;

import java.util.List;

import org.alloy.metal.configuration.ConfigurationLocation;

import com.google.common.collect.Lists;

public class CoreFacet implements PrimaryModuleFacet {
	protected List<ConfigurationLocation> configurationLocations = Lists.newArrayList();
	protected List<ConfigurationLocation> log4jConfigurationLocations = Lists.newArrayList();
	protected List<ConfigurationLocation> propertyConfigurationLocations = Lists.newArrayList();

	@Override
	public List<ConfigurationLocation> getConfigurationLocations() {
		return configurationLocations;
	}

	@Override
	public List<ConfigurationLocation> getPropertyConfigurationLocations() {
		return propertyConfigurationLocations;
	}

	@Override
	public List<ConfigurationLocation> getLog4jConfigurationLocations() {
		return log4jConfigurationLocations;
	}
}
