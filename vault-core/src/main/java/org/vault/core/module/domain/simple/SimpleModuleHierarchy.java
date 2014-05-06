package org.vault.core.module.domain.simple;

import org.vault.base.collections.tree.Tree;
import org.vault.base.module.domain.Module;
import org.vault.base.module.domain.ModuleHierarchy;

public class SimpleModuleHierarchy implements ModuleHierarchy {
	private Tree<Module> modules;

	public Tree<Module> getModules() {
		return modules;
	}

	public void setModules(Tree<Module> modules) {
		this.modules = modules;
	}
}
