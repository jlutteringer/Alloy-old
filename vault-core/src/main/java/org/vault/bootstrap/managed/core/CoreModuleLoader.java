package org.vault.bootstrap.managed.core;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vault.base.collections.lists.VLists;
import org.vault.base.collections.tree.Tree;
import org.vault.base.collections.tree.Trees;
import org.vault.base.module.domain.Module;
import org.vault.base.module.domain.ModuleHierarchy;
import org.vault.base.module.service.ModuleLoader;
import org.vault.bootstrap.managed.core.DependencyResolver.DependencyResolverState;
import org.vault.core.module.domain.simple.SimpleModuleHierarchy;

import com.google.common.collect.Lists;

@Service
public class CoreModuleLoader implements ModuleLoader {
	private static final Logger logger = LogManager.getLogger(CoreModuleLoader.class);

	@Autowired
	private DependencyResolver dependencyResolver;

	@Autowired
	private ModuleContext moduleContext;

	private ModuleHierarchy heirarchy = null;

	@Override
	public List<Module> getModuleLoadOrder() {
		return Lists.newArrayList(Trees.iterateBreadthFirst(heirarchy.getModules()));
	}

	@PostConstruct
	private void initialize() {
		heirarchy = new SimpleModuleHierarchy();
		heirarchy.setModules(this.buildModuleTree());

		logger.debug("Built module hierarchy:");
		Trees.iterateBreadthFirst(heirarchy.getModules()).forEach((module) -> logger.debug(module));
	}

	private Tree<Module> buildModuleTree() {
		DependencyResolverState state =
				dependencyResolver.resolveDependencies(moduleContext.getDependencies(moduleContext.getApplicationModule()));

		LinkedList<Module> modulesToAdd =
				Lists.newLinkedList(state.getAll(moduleContext.getApplicationModule().getDependencies()));

		Tree<Module> moduleTree = Trees.newHashTree(moduleContext.getCoreModule());

		while (!modulesToAdd.isEmpty()) {
			Module module = modulesToAdd.pop();
			Collection<Module> dependencies = VLists.list(state.getAll(moduleContext.getDependencies(module)));
			if (moduleTree.containsAll(dependencies)) {
				Tree<Module> childTree = Trees.newHashTree(module);
				for (Module dependency : dependencies) {
					moduleTree.findSubTree(dependency).addChild(childTree);
				}
			}
		}

		return moduleTree;
	}
}