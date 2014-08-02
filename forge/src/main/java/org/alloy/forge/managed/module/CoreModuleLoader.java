package org.alloy.forge.managed.module;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;

import org.alloy.forge.managed.module.DependencyResolver.DependencyResolverState;
import org.alloy.forge.module.Dependency;
import org.alloy.forge.module.Module;
import org.alloy.forge.module.ModuleHierarchy;
import org.alloy.forge.module.ModuleLoader;
import org.alloy.forge.module.SimpleModuleHierarchy;
import org.alloy.metal.collections.lists._List;
import org.alloy.metal.collections.tree.Tree;
import org.alloy.metal.collections.tree._Tree;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		return Lists.newArrayList(_Tree.iterateBreadthFirst(heirarchy.getModules()));
	}

	@PostConstruct
	private void initialize() {
		heirarchy = new SimpleModuleHierarchy();
		heirarchy.setModules(this.buildModuleTree());

		logger.debug("Built module hierarchy:");
		_Tree.iterateBreadthFirst(heirarchy.getModules()).forEach((module) -> logger.debug(module));
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

		Tree<Module> moduleTree = _Tree.newHashTree(moduleContext.getCoreModule());

		while (!modulesToAdd.isEmpty()) {
			Module module = modulesToAdd.pop();
			if (!moduleTree.contains(module)) {
				logger.debug("Considering module [" + module + "] to add to hierarchy");
				Collection<Module> dependencies = _List.list(state.getAll(moduleContext.getDependencies(module)));
				if (moduleTree.containsAll(dependencies)) {
					Tree<Module> childTree = _Tree.newHashTree(module);
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