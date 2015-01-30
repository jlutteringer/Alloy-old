package org.alloy.forge.managed.module;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.alloy.forge.module.ApplicationModule;
import org.alloy.forge.module.CoreModule;
import org.alloy.forge.module.DefaultApplicationModule;
import org.alloy.forge.module.Dependencies;
import org.alloy.forge.module.Dependency;
import org.alloy.forge.module.DependencyResolutionException;
import org.alloy.forge.module.Module;
import org.alloy.forge.module.ModuleType;
import org.alloy.forge.module.ResolvedDependency;
import org.alloy.metal.collections.iterable._Iterable;
import org.alloy.metal.collections.lists._Lists;
import org.alloy.metal.facets.FacetProvider;
import org.alloy.metal.spring.AlloyBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Sets;

@Service
public class CoreModuleContext extends AlloyBean implements ModuleContext {
	private Set<Module> modules = Sets.newHashSet();

	@Autowired
	private CoreModule coreModule;

	@Autowired(required = false)
	private ApplicationModule applicationModule;

	@Autowired
	private FacetProvider facetProvider;

	@Autowired
	private DependencyResolver dependencyResolver;

	@Autowired
	private void setModules(List<Module> modules) {
		for (Module module : modules) {
			if (ModuleType.MODULE.equals(module.getType()) || ModuleType.PATCH.equals(module.getType())) {
				logger.info("Detected [" + module.getFriendlyName() + "] module");
				this.modules.add(module);
			}
		}
	}

	@PostConstruct
	private void initialize() {
		if (applicationModule == null) {
			logger.info("Registering default application module");
			applicationModule = new DefaultApplicationModule(facetProvider);

			// The default application module depends on all modules with a type of module
			applicationModule.getDependencies().addAll(
					Dependencies.of(
							_Iterable.filter(modules, (module) -> module.getType().equals(ModuleType.MODULE))));
		}
	}

	@Override
	public CoreModule getCoreModule() {
		return coreModule;
	}

	@Override
	public ApplicationModule getApplicationModule() {
		return applicationModule;
	}

	@Override
	public Collection<Module> getModules() {
		return Collections.unmodifiableList(_Lists.list(modules, coreModule, applicationModule));
	}

	@Override
	public Module getModule(Class<? extends Module> type) {
		for (Module module : this.getModules()) {
			if (module.getClass().equals(type)) {
				return module;
			}
		}

		throw new DependencyResolutionException("Unable to find module of type " + type);
	}

	@Override
	public Module getModule(String name) {
		for (Module module : this.getModules()) {
			if (module.getName().equals(name)) {
				return module;
			}
		}

		throw new DependencyResolutionException("Unable to find module with name " + name);
	}

	@Override
	public Collection<Dependency> getDependencies(Module module) {
		List<Dependency> dependencies = _Lists.list(module.getDependencies());
		// Everything depends on core
		dependencies.add(new ResolvedDependency(this.getCoreModule()));
		return dependencies;
	}
}