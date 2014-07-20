package org.vault.bootstrap.managed.core;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.vault.base.collections.iterable.VIterables;
import org.vault.base.delegator.AbstractDelegator;
import org.vault.base.delegator.ClassTypeDelegate;
import org.vault.base.module.domain.ConditionalDependency;
import org.vault.base.module.domain.Dependency;
import org.vault.base.module.domain.Module;
import org.vault.base.module.domain.NameDependency;
import org.vault.base.module.domain.ResolvedDependency;
import org.vault.base.module.domain.SimpleDependency;
import org.vault.base.module.domain.WeakDependency;
import org.vault.base.spring.beans.VaultBean;
import org.vault.base.utilities.function.VPredicates;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@Component
public class DependencyResolver extends VaultBean {
	@Autowired
	private DependencyResolverDelegator delegator;

	@Autowired
	private ModuleContext moduleContext;

	public DependencyResolverState resolveDependencies(Iterable<Dependency> dependencies) {
		LinkedList<Dependency> dependenciesToResolve = Lists.newLinkedList(dependencies);
		DependencyResolverState state = new DependencyResolverState();

		Predicate<Collection<Dependency>> condition = (inputs) -> {
			for (Dependency input : inputs) {
				if (!this.isOptional(input)) {
					return true;
				}
			}

			return false;
		};

		while (condition.test(dependenciesToResolve)) {
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

		DependencyResolution resolution = delegator.getDelegate(dependency).getResolution(state, dependency);
		if (resolution.getResolvedModule() != null) {
			logger.debug("Resolved dependency [" + dependency + "] to module [" + resolution.getResolvedModule() + "]");
			state.put(dependency, resolution.getResolvedModule());

			for (Dependency subDependency : resolution.getResolvedModule().getDependencies()) {
				if (!state.containsDependency(subDependency)) {
					resolution.getDependenciesToResolve().add(subDependency);
				}
			}
		}
		else {
			logger.debug("Unable to resolve dependency [" + dependency + "] - readding to queue");
			resolution.getDependenciesToResolve().add(dependency);
		}

		return resolution;
	}

	public boolean isOptional(Dependency dependency) {
		return delegator.getDelegate(dependency).isOptional();
	}

	@Component
	class DependencyResolverDelegator extends AbstractDelegator<DependencyDelegate<?>, Dependency> {

	}

	abstract class DependencyDelegate<T> extends ClassTypeDelegate<T, Dependency> {
		@SuppressWarnings("unchecked")
		public DependencyResolution getResolution(DependencyResolverState state, Dependency dependency) {
			return this.getModuleInternal(state, (T) dependency);
		}

		public boolean isOptional() {
			return false;
		}

		public abstract DependencyResolution getModuleInternal(DependencyResolverState state, T dependency);
	}

	@Component
	class SimpleDependencyDelegate extends DependencyDelegate<SimpleDependency> {
		@Override
		public DependencyResolution getModuleInternal(DependencyResolverState state, SimpleDependency dependency) {
			return new DependencyResolution(dependency, moduleContext.getModule(dependency.getType()));
		}
	}

	@Component
	class NameDependencyDelegate extends DependencyDelegate<NameDependency> {
		@Override
		public DependencyResolution getModuleInternal(DependencyResolverState state, NameDependency dependency) {
			return new DependencyResolution(dependency, moduleContext.getModule(dependency.getName()));
		}
	}

	@Component
	class ResolvedDependencyDelegate extends DependencyDelegate<ResolvedDependency> {
		@Override
		public DependencyResolution getModuleInternal(DependencyResolverState state, ResolvedDependency dependency) {
			return new DependencyResolution(dependency, dependency.getModule());
		}
	}

	@Component
	class WeakDependencyDelegate extends DependencyDelegate<WeakDependency> {
		@Override
		public DependencyResolution getModuleInternal(DependencyResolverState state, WeakDependency dependency) {
			if (state.containsDependency(dependency.getTargetDependency())) {
				return new DependencyResolution(dependency, state.get(dependency.getTargetDependency()));
			}
			else {
				return new DependencyResolution(dependency);
			}
		}

		@Override
		public boolean isOptional() {
			return true;
		}
	}

	@Component
	class ConditionalDependencyDelegate extends DependencyDelegate<ConditionalDependency> {
		@Override
		public DependencyResolution getModuleInternal(DependencyResolverState state, ConditionalDependency dependency) {
			if (state.containsDependency(dependency.getConditionDependency())) {
				if (state.containsDependency(dependency.getTargetDependency())) {
					return new DependencyResolution(dependency, state.get(dependency.getTargetDependency()));
				}
				else {
					return new DependencyResolution(dependency, dependency.getTargetDependency());
				}
			}
			else {
				return new DependencyResolution(dependency);
			}
		}

		@Override
		public boolean isOptional() {
			return true;
		}
	}

	public static final class DependencyResolution {
		private Module resolvedModule;
		private Dependency resolvingDependency;
		private List<Dependency> dependenciesToResolve = Lists.newArrayList();

		public DependencyResolution(Dependency resolvingDependency, Dependency... dependenciesToResolve) {
			this.resolvingDependency = resolvingDependency;
			this.dependenciesToResolve.addAll(Arrays.asList(dependenciesToResolve));
		}

		public DependencyResolution(Dependency resolvingDependency, Module resolvedModule) {
			super();
			this.resolvingDependency = resolvingDependency;
			this.resolvedModule = resolvedModule;
		}

		public Module getResolvedModule() {
			return resolvedModule;
		}

		public Dependency getResolvingDependency() {
			return resolvingDependency;
		}

		public List<Dependency> getDependenciesToResolve() {
			return dependenciesToResolve;
		}
	}

	public static class DependencyResolverState {
		private HashMap<Dependency, Module> backingMap = Maps.newHashMap();

		public boolean containsDependency(Dependency dependency) {
			return backingMap.containsKey(dependency);
		}

		public void put(Dependency dependency, Module module) {
			backingMap.put(dependency, module);
		}

		public Module get(Dependency dependency) {
			return backingMap.get(dependency);
		}

		public Iterable<Module> getAll(Iterable<Dependency> dependencies) {
			return VIterables.transform(VIterables.filter(dependencies, VPredicates.isDefined()), this::get);
		}
	}
}