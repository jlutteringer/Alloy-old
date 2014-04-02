package org.vault.core.module.service;

import java.util.List;

import org.vault.base.utilities.configuration.ConfigurationLocation;
import org.vault.core.module.domain.ModuleHierarchy;

public interface ModuleLoader {
	public ModuleHierarchy getModuleHierarchy();

	public List<ConfigurationLocation> buildConfigurationLocations(ModuleHierarchy moduleHierarchy);
}
