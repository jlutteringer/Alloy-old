package org.vault.bootstrap.managed.core;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.vault.base.collections.iterable.VIterables;
import org.vault.base.collections.lists.VLists;
import org.vault.base.delegator.AbstractDelegator;
import org.vault.base.delegator.ClassTypeDelegate;
import org.vault.base.module.domain.ConditionalDependency;
import org.vault.base.module.domain.Dependency;
import org.vault.base.module.domain.Module;
import org.vault.base.module.domain.NameDependency;
import org.vault.base.module.domain.SimpleDependency;
import org.vault.base.utilities.VOptional;

import com.google.common.collect.Lists;

@Component
public class DependencyResolver {
	@Autowired
	private DependencyResolverDelegator delegator;

	public List<Module> resolveDependencies(List<Dependency> dependencies) {
		List<VOptional<Module>> resolvedModules = Lists.newArrayList();
		dependencies.forEach((dependency) -> {
			resolvedModules.add(delegator.getDelegate(dependency).getModule(dependency));
		});

		return VLists.list(VIterables.flatten(resolvedModules));
	}

	@Component
	class DependencyResolverDelegator extends AbstractDelegator<DependencyDelegate<?>, Dependency> {

	}

	abstract class DependencyDelegate<T> extends ClassTypeDelegate<T, Dependency> {
		@SuppressWarnings("unchecked")
		public VOptional<Module> getModule(Dependency dependency) {
			return this.getModuleInternal((T) dependency);
		}

		public abstract VOptional<Module> getModuleInternal(T dependency);
	}

	@Component
	class SimpleDependencyDelegate extends DependencyDelegate<SimpleDependency> {
		@Override
		public VOptional<Module> getModuleInternal(SimpleDependency dependency) {
			// TODO Auto-generated method stub
			return null;
		}
	}

	@Component
	class NameDependencyDelegate extends DependencyDelegate<NameDependency> {
		@Override
		public VOptional<Module> getModuleInternal(NameDependency dependency) {
			// TODO Auto-generated method stub
			return null;
		}
	}

	@Component
	class ConditionalDependencyDelegate extends DependencyDelegate<ConditionalDependency> {
		@Override
		public VOptional<Module> getModuleInternal(ConditionalDependency dependency) {
			// TODO Auto-generated method stub
			return null;
		}
	}
}