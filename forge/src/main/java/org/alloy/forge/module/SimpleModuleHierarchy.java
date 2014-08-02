package org.alloy.forge.module;

import org.alloy.metal.collections.tree.Tree;

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
