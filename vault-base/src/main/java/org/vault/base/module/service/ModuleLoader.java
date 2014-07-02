package org.vault.base.module.service;

import java.util.List;

import org.vault.base.module.domain.Module;

public interface ModuleLoader {
	public List<Module> getModuleLoadOrder();

	public List<Module> getModulesByKeys(List<String> moduleNames);
}
