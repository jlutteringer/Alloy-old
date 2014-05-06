package org.vault.core.module.domain;

import java.util.List;

import org.vault.base.utilities.configuration.ConfigurationLocation;

public interface Module {
	public String getName();

	public String getFriendlyName();

	public ModuleType getType();

	public List<Module> getDependencies();

	public List<ConfigurationLocation> getBaseConfigurationLocations();

	public List<ConfigurationLocation> getModuleConfigurationLocations();

	public List<ConfigurationLocation> getLog4jConfigurationLocations();
}