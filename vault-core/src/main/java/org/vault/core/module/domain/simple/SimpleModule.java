package org.vault.core.module.domain.simple;

import java.util.List;

import org.vault.base.utilities.configuration.ConfigurationLocation;
import org.vault.core.module.domain.Module;
import org.vault.core.module.domain.ModuleType;

import com.google.common.collect.Lists;

public class SimpleModule implements Module {
	public String name;
	public ModuleType type = ModuleType.MODULE;
	public List<ConfigurationLocation> configurationLocations = Lists.newArrayList();
	public List<Module> dependencies = Lists.newArrayList();

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

	public List<Module> getDependencies() {
		return dependencies;
	}

	public void setDependencies(List<Module> dependencies) {
		this.dependencies = dependencies;
	}

	@Override
	public String toString() {
		return name + " " + type;
	}
}
