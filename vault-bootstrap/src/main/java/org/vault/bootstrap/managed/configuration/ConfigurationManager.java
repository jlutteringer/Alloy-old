package org.vault.bootstrap.managed.configuration;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.vault.base.module.domain.Module;
import org.vault.base.module.service.ModuleLoader;
import org.vault.base.utilities.configuration.ConfigurationLocation;

import com.google.common.collect.Lists;

public abstract class ConfigurationManager {
	@Autowired
	private ModuleLoader moduleLoader;

	public List<ConfigurationLocation> buildConfigurationLocations() {
		List<ConfigurationLocation> configurationLocations = Lists.newArrayList();
		for (Module module : moduleLoader.getModuleLoadOrder()) {
			configurationLocations.addAll(getDefaultConfigurationLocations(module));
			configurationLocations.addAll(getSpecificConfigurationLocations(module));
		}

		return configurationLocations;
	}

	protected abstract List<ConfigurationLocation> getSpecificConfigurationLocations(Module module);

	protected abstract List<ConfigurationLocation> getDefaultConfigurationLocations(Module module);
}