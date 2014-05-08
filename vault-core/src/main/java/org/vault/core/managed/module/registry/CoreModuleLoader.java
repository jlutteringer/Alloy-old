package org.vault.core.managed.module.registry;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vault.base.collections.tree.Tree;
import org.vault.base.collections.tree.Trees;
import org.vault.base.module.domain.Module;
import org.vault.base.module.domain.ModuleHierarchy;
import org.vault.base.module.domain.ModuleType;
import org.vault.base.module.service.ModuleLoader;
import org.vault.core.module.domain.simple.ApplicationModule;
import org.vault.core.module.domain.simple.SimpleModuleHierarchy;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

@Service
public class CoreModuleLoader implements ModuleLoader {
	private static final Logger log = Logger.getLogger(CoreModuleLoader.class);

	@Autowired
	private CoreModule coreModule;

	@Autowired(required = false)
	private ApplicationModule applicationModule;

	private Map<String, Module> modules;

	private ModuleHierarchy heirarchy = null;

	@Autowired
	private void setModules(List<Module> modules) {
		Map<String, Module> filteredModules = Maps.newHashMap();
		for (Module module : modules) {
			if (ModuleType.MODULE.equals(module.getType())) {
				log.info("Detected " + module.getFriendlyName() + " module. Adding to configuration.");
				filteredModules.put(module.getName(), module);
			}
		}

		this.modules = filteredModules;
	}

	@PostConstruct
	private void initialize() {
		if (applicationModule == null) {
			applicationModule = new ApplicationModule();
		}

		heirarchy = new SimpleModuleHierarchy();
		heirarchy.setModules(this.buildModuleTree());
	}

	@Override
	public ModuleHierarchy getModuleHierarchy() {
		return heirarchy;
	}

	@Override
	public List<Module> getModulesByKeys(List<String> moduleNames) {
		List<Module> matchedModules = Lists.newArrayList();
		for (String moduleName : moduleNames) {
			matchedModules.add(modules.get(moduleName));
		}
		return matchedModules;
	}

	private Tree<Module> buildModuleTree() {
		Tree<Module> moduleTree = Trees.<Module> newHashTree(coreModule);
		while (!moduleTree.containsAll(modules.values())) {
			for (Module module : modules.values()) {
				Set<Module> dependencies = this.getDependencies(module);
				if (moduleTree.containsAll(dependencies)) {
					for (Module dependency : dependencies) {
						moduleTree.findSubTree(dependency).add(module);
					}
				}
			}
		}

		return moduleTree;
	}

	private Set<Module> getDependencies(Module module) {
		Set<Module> dependencies = Sets.newHashSet();
		Set<Module> implicitDependencies = Sets.<Module> newHashSet(coreModule);
		for (Module dependency : this.getModulesByKeys(module.getDependencies())) {
			implicitDependencies.addAll(getFullDependencyHierarchy(dependency));
		}

		for (Module dependency : this.getModulesByKeys(module.getDependencies())) {
			if (!implicitDependencies.contains(dependency)) {
				dependencies.add(dependency);
			}
		}

		if (dependencies.isEmpty()) {
			dependencies.add(coreModule);
		}

		return dependencies;
	}

	private Set<Module> getFullDependencyHierarchy(Module module) {
		Set<Module> dependencies = Sets.newHashSet();
		for (Module dependency : this.getModulesByKeys(module.getDependencies())) {
			dependencies.add(dependency);
			dependencies.addAll(this.getFullDependencyHierarchy(dependency));
		}
		return dependencies;
	}
}
