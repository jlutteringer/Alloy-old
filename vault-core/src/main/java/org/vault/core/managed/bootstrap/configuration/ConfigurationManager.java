package org.vault.core.managed.bootstrap.configuration;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.vault.base.collections.tree.Trees;
import org.vault.base.utilities.configuration.ConfigurationLocation;
import org.vault.core.module.domain.Module;
import org.vault.core.module.service.ModuleLoader;

import com.google.common.collect.Lists;

public abstract class ConfigurationManager {
	@Autowired
	private ModuleLoader moduleLoader;

	public List<ConfigurationLocation> buildConfigurationLocations() {
		List<ConfigurationLocation> configurationLocations = Lists.newArrayList();
		for (Module module : Trees.iterateBreadthFirst(moduleLoader.getModuleHierarchy().getModules())) {
			configurationLocations.addAll(getDefaultConfigurationLocations(module));
			configurationLocations.addAll(getSpecificConfigurationLocations(module));
		}

		return configurationLocations;
	}

	protected abstract List<ConfigurationLocation> getSpecificConfigurationLocations(Module module);

	protected abstract List<ConfigurationLocation> getDefaultConfigurationLocations(Module module);
}