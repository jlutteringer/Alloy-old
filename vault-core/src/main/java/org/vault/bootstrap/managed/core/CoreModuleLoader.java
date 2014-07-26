package org.vault.bootstrap.managed.core;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vault.base.collections.lists.VLists;
import org.vault.base.collections.tree.Tree;
import org.vault.base.collections.tree.Trees;
import org.vault.base.module.domain.Dependency;
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

		logger.debug("Dependency resolver state:");
		for (Entry<Dependency, Module> entry : state.getEntries()) {
			logger.printf(Level.DEBUG, "Dependency [%s] => Module [%s]", entry.getKey(), entry.getValue());
		}

		LinkedList<Module> modulesToAdd =
				Lists.newLinkedList(state.getAll(moduleContext.getApplicationModule().getDependencies()));

		modulesToAdd.add(moduleContext.getApplicationModule());

		Tree<Module> moduleTree = Trees.newHashTree(moduleContext.getCoreModule());

		while (!modulesToAdd.isEmpty()) {
			Module module = modulesToAdd.pop();
			if (!moduleTree.contains(module)) {
				logger.debug("Considering module [" + module + "] to add to hierarchy");
				Collection<Module> dependencies = VLists.list(state.getAll(moduleContext.getDependencies(module)));
				if (moduleTree.containsAll(dependencies)) {
					Tree<Module> childTree = Trees.newHashTree(module);
					for (Module dependency : dependencies) {
						moduleTree.findSubTree(dependency).addChild(childTree);
					}
				}
				else {
					dependencies.removeAll(moduleTree);
					logger.debug("Dependencies " + dependencies + " have not yet been parsed, readding to queue");
					modulesToAdd.addAll(dependencies);
					modulesToAdd.add(module);
				}
			}
		}

		return moduleTree;
	}
}