package org.vault.base.module.domain;

import java.util.List;

import org.vault.base.collections.iterable.VIterables;

import com.google.common.collect.Lists;

public class Dependencies {
	public static DependencyBuilder1 builder() {
		return new DependencyBuilder1();
	}

	public static Dependency of(Class<? extends Module> type) {
		SimpleDependency dependency = new SimpleDependency();
		dependency.setType(type);
		return dependency;
	}

	public static Dependency of(String name) {
		NameDependency dependency = new NameDependency();
		dependency.setName(name);
		return dependency;
	}

	public static List<Dependency> of(Iterable<Module> modules) {
		return Lists.newArrayList(VIterables.transform(modules, ResolvedDependency::new));
	}

	public static <T extends DependencyDecorator> T decorate(Dependency dependency, T decorator) {
		decorator.setTargetDependency(dependency);
		return decorator;
	}

	public static class DependencyBuilder1 {
		public DependencyBuilder2 of(Class<? extends Module> type) {
			return new DependencyBuilder2(Dependencies.of(type));
		}

		public DependencyBuilder2 of(String name) {
			return new DependencyBuilder2(Dependencies.of(name));
		}
	}

	public static class DependencyBuilder2 {
		private Dependency dependency;

		public DependencyBuilder2(Dependency dependency) {
			this.dependency = dependency;
		}

		public DependencyBuilder2 weak() {
			this.dependency = Dependencies.decorate(dependency, new WeakDependency());
			return this;
		}

		public DependencyBuilder2 conditional(Dependency includeIfPresent) {
			ConditionalDependency decoratedDependency = Dependencies.decorate(includeIfPresent, new ConditionalDependency());
			decoratedDependency.setConditionDependency(includeIfPresent);
			this.dependency = decoratedDependency;
			return this;
		}

		public DependencyBuilder2 conditional(Class<? extends Module> type) {
			return this.conditional(Dependencies.of(type));
		}

		public DependencyBuilder2 conditional(String name) {
			return this.conditional(Dependencies.of(name));
		}

		public Dependency create() {
			return dependency;
		}
	}
}