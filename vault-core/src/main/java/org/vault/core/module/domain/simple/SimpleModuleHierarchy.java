package org.vault.core.module.domain.simple;

import org.alloy.metal.collections.tree.Tree;
import org.vault.base.module.domain.Module;
import org.vault.base.module.domain.ModuleHierarchy;

public class SimpleModuleHierarchy implements ModuleHierarchy {
	private Tree<Module> modules;

	@Override
	public Tree<Module> getModules() {
		return modules;
	}

	@Override
	public void setModules(Tree<Module> modules) {
		this.modules = modules;
	}
}
