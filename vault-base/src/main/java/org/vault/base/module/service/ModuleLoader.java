package org.vault.base.module.service;

import java.util.List;

import org.vault.base.module.domain.Module;
import org.vault.base.module.domain.ModuleHierarchy;

public interface ModuleLoader {
	public ModuleHierarchy getModuleHierarchy();

	public List<Module> getModulesByKeys(List<String> moduleNames);
}
