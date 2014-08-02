package org.alloy.forge.module;

import java.util.List;

public interface ModuleLoader {
	public List<Module> getModuleLoadOrder();
}