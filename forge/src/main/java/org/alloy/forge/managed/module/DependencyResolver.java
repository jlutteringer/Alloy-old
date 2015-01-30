package org.alloy.forge.managed.module;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.alloy.forge.module.ConditionalDependency;
import org.alloy.forge.module.Dependency;
import org.alloy.forge.module.DependencyResolutionException;
import org.alloy.forge.module.Module;
import org.alloy.forge.module.NameDependency;
import org.alloy.forge.module.ResolvedDependency;
import org.alloy.forge.module.TypeDependency;
import org.alloy.forge.module.WeakDependency;
import org.alloy.metal.collections._Collections;
import org.alloy.metal.collections.iterable._Iterable;
import org.alloy.metal.function.Condition;
import org.alloy.metal.spring.AlloyBean;
import org.alloy.metal.spring.delegator.AbstractDelegator;
import org.alloy.metal.spring.delegator.ClassTypeDelegate;
import org.apache.logging.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

@Component
public class DependencyResolver extends AlloyBean {
	@Autowired
	private DependencyResolverDelegator delegator;

	public DependencyResolverState resolveDependencies(Iterable<Dependency> dependencies) {
		LinkedList<Dependency> dependenciesToResolve = Lists.newLinkedList(dependencies);
		DependencyResolverState state = new DependencyResolverState();

		Condition hasChanged = _Collections.monitor(dependenciesToResolve).hasChanged();
		while (!dependenciesToResolve.isEmpty() && hasChanged.test()) {
			Dependency dependency = dependenciesToResolve.pop();
			DependencyResolution resolution = this.resolveDependency(state, dependency);
			dependenciesToResolve.addAll(resolution.getDependenciesToResolve());
		}

		dependenciesToResolve.forEach((dependency) -> logger.debug("Skipping unresolved optional dependency [" + dependency + "]"));
		return state;
	}

	protected DependencyResolution resolveDependency(DependencyResolverState state, Dependency dependency) {
		if (state.containsDependency(dependency)) {
			return new DependencyResolution(dependency, state.get(dependency));
		}

		logger.debug("Attempting to resolve dependency [" + dependency + "]...");

		try {
			DependencyResolution resolution = delegator.getDelegate(dependency).getResolution(state, dependency);
			if (resolution.getResolvedModule() != null) {
				logger.debug("Resolved dependency [" + dependency + "] to module [" + resolution.getResolvedModule() + "]");
				state.put(dependency, resolution.getResolvedModule());

				for (Dependency subDependency : resolution.getResolvedModule().getDependencies()) {
					if (!state.containsDependency(subDependency)) {
						logger.debug("Adding sub-dependency to resolve: [" + subDependency + "]");
						resolution.getDependenciesToResolve().add(subDependency);
					}
				}
			}
			else {
				logger.debug("Unable to resolve dependency [" + dependency + "]");
				resolution.getDependenciesToResolve().add(dependency);
			}

			return resolution;

		} catch (DependencyResolutionException e) {
			if (dependency.isOptional()) {
				logger.printf(Level.DEBUG, "Unable to resolve optional dependency [%s] - we're skipping it", dependency);
				state.getUnresolvableDependencies().add(dependency);
				return new DependencyResolution(dependency);
			}
			else {
				throw e;
			}
		}
	}

	@Component
	static class DependencyResolverDelegator extends AbstractDelegator<DependencyDelegate<?>, Dependency> {

	}

	abstract static class DependencyDelegate<T> extends ClassTypeDelegate<T, Dependency> {
		@Autowired
		protected ModuleContext moduleContext;

		@SuppressWarnings("unchecked")
		public DependencyResolution getResolution(DependencyResolverState state, Dependency dependency) {
			return this.getModuleInternal(state, (T) dependency);
		}

		public abstract DependencyResolution getModuleInternal(DependencyResolverState state, T dependency);
	}

	@Component
	static class SimpleDependencyDelegate extends DependencyDelegate<TypeDependency> {
		@Override
		public DependencyResolution getModuleInternal(DependencyResolverState state, TypeDependency dependency) {
			return new DependencyResolution(dependency, moduleContext.getModule(dependency.getType()));
		}
	}

	@Component
	static class NameDependencyDelegate extends DependencyDelegate<NameDependency> {
		@Override
		public DependencyResolution getModuleInternal(DependencyResolverState state, NameDependency dependency) {
			return new DependencyResolution(dependency, moduleContext.getModule(dependency.getName()));
		}
	}

	@Component
	static class ResolvedDependencyDelegate extends DependencyDelegate<ResolvedDependency> {
		@Override
		public DependencyResolution getModuleInternal(DependencyResolverState state, ResolvedDependency dependency) {
			return new DependencyResolution(dependency, dependency.getModule());
		}
	}

	@Component
	static class WeakDependencyDelegate extends DependencyDelegate<WeakDependency> {
		@Override
		public DependencyResolution getModuleInternal(DependencyResolverState state, WeakDependency dependency) {
			List<Dependency> dependenciesToResolve = Lists.newArrayList();
			boolean pendingDependencyResolution = false;

			if (state.shouldAttemptResolution(dependency.getTargetDependency())) {
				pendingDependencyResolution = true;
				dependenciesToResolve.add(dependency.getTargetDependency());
			}

			if (pendingDependencyResolution) {
				return new DependencyResolution(dependency, dependenciesToResolve);
			}

			if (state.hasRequiredDependencyForModule(state.get(dependency.getTargetDependency()))) {
				return new DependencyResolution(dependency, state.get(dependency.getTargetDependency()));
			}
			else {
				return new DependencyResolution(dependency);
			}
		}
	}

	@Component
	static class ConditionalDependencyDelegate extends DependencyDelegate<ConditionalDependency> {
		@Override
		public DependencyResolution getModuleInternal(DependencyResolverState state, ConditionalDependency dependency) {
			List<Dependency> dependenciesToResolve = Lists.newArrayList();
			boolean pendingDependencyResolution = false;

			if (state.shouldAttemptResolution(dependency.getConditionDependency())) {
				logger.printf(Level.DEBUG, "For dependency [%s] dependency [%s] must be resolved first", dependency, dependency.getConditionDependency());
				pendingDependencyResolution = true;
				dependenciesToResolve.add(dependency.getConditionDependency());
			}
			if (state.shouldAttemptResolution(dependency.getTargetDependency())) {
				logger.printf(Level.DEBUG, "For dependency [%s] dependency [%s] must be resolved first", dependency, dependency.getTargetDependency());
				pendingDependencyResolution = true;
				dependenciesToResolve.add(dependency.getTargetDependency());
			}

			if (pendingDependencyResolution) {
				return new DependencyResolution(dependency, dependenciesToResolve);
			}

			Module module = state.get(dependency.getConditionDependency());
			logger.printf(Level.DEBUG, "All pending dependencies handled for dependency [%s]", dependency);
			if (state.hasRequiredDependencyForModule(module)) {
				return new DependencyResolution(dependency, state.get(dependency.getTargetDependency()));
			}
			else {
				logger.printf(Level.DEBUG, "Must have required dependency for module [%s] to resolve dependency [%s]", module, dependency);
				return new DependencyResolution(dependency);
			}
		}
	}

	public static final class DependencyResolution {
		private Module resolvedModule;
		private Dependency resolvingDependency;
		private List<Dependency> dependenciesToResolve = Lists.newArrayList();

		public DependencyResolution(Dependency resolvingDependency, Dependency... dependenciesToResolve) {
			this(resolvingDependency, Arrays.asList(dependenciesToResolve));
		}

		public DependencyResolution(Dependency resolvingDependency, Collection<Dependency> dependenciesToResolve) {
			this.resolvingDependency = resolvingDependency;
			this.dependenciesToResolve.addAll(dependenciesToResolve);
		}

		public DependencyResolution(Dependency resolvingDependency, Module resolvedModule) {
			super();
			this.resolvingDependency = resolvingDependency;
			this.resolvedModule = resolvedModule;
		}

		public Module getResolvedModule() {
			return resolvedModule;
		}

		public Dependency getRootResolvingDependency() {
			return resolvingDependency;
		}

		public List<Dependency> getDependenciesToResolve() {
			return dependenciesToResolve;
		}
	}

	public static class DependencyResolverState {
		private HashMap<Dependency, Module> backingMap = Maps.newHashMap();
		private Set<Dependency> unresolvableDependencies = Sets.newHashSet();

		public boolean containsDependency(Dependency dependency) {
			return backingMap.containsKey(dependency);
		}

		public boolean hasRequiredDependencyForModule(Module module) {
			if (module == null) {
				return false;
			}

			for (Entry<Dependency, Module> entry : backingMap.entrySet()) {
				if (entry.getValue().equals(module)) {
					if (!entry.getKey().isOptional()) {
						return true;
					}
				}
			}
			return false;
		}

		public boolean shouldAttemptResolution(Dependency dependency) {
			if (!this.containsDependency(dependency) && !this.getUnresolvableDependencies().contains(dependency)) {
				return true;
			}
			return false;
		}

		public void put(Dependency dependency, Module module) {
			backingMap.put(dependency, module);
		}

		public Module get(Dependency dependency) {
			return backingMap.get(dependency);
		}

		public Iterable<Module> getAll(Iterable<Dependency> dependencies) {
			return _Iterable.transform(_Iterable.filter(dependencies, (dependency) -> {
				if (this.containsDependency(dependency)) {
					return true;
				} else {
					if (!dependency.isOptional()) {
						throw new DependencyResolutionException("Required dependency " + dependency + " not found in state");
					}
					return false;
				}
			}), this::get);
		}

		public Collection<Entry<Dependency, Module>> getEntries() {
			return backingMap.entrySet();
		}

		public Set<Dependency> getUnresolvableDependencies() {
			return unresolvableDependencies;
		}
	}
}