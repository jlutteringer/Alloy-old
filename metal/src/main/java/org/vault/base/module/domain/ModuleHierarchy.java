package org.vault.base.module.domain;

import org.alloy.metal.collections.tree.Tree;

public interface ModuleHierarchy {
	public Tree<Module> getModules();

	public void setModules(Tree<Module> moduleTree);
}