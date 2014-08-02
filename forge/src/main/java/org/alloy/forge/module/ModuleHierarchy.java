package org.alloy.forge.module;

import org.alloy.metal.collections.tree.Tree;

public interface ModuleHierarchy {
	public Tree<Module> getModules();

	public void setModules(Tree<Module> moduleTree);
}