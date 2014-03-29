package org.vault.core.module.domain.simple;

import java.util.List;

import org.vault.base.utilities.configuration.ConfigurationLocation;
import org.vault.core.module.domain.Module;
import org.vault.core.module.domain.ModuleType;

public class SimpleModule implements Module {
	public String name;
	public ModuleType type;
	public List<ConfigurationLocation> configurationLocations;

	public String getName() {
		return name;
	}

	public ModuleType getType() {
		return type;
	}

	public List<ConfigurationLocation> getConfigurationLocations() {
		return configurationLocations;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setType(ModuleType type) {
		this.type = type;
	}

	public void setConfigurationLocations(List<ConfigurationLocation> configurationLocations) {
		this.configurationLocations = configurationLocations;
	}
}
