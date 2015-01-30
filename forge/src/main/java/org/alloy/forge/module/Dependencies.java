package org.alloy.forge.module;

import java.util.List;

import org.alloy.metal.collections.iterable._Iterable;
import org.metal.function.equality.EqualityMapping;

import com.google.common.collect.Lists;

public class Dependencies {
	private static EqualityMapping<Dependency> EQUALITY = new EqualityMapping<>();
	static {
		EQUALITY.addEquality(NameDependency.class,
				(first, second) -> first.getName().equals(second.getName()));

		EQUALITY.addEquality(ResolvedDependency.class,
				(first, second) -> first.getModule().equals(second.getModule()));

		EQUALITY.addEquality(TypeDependency.class,
				(first, second) -> first.getType().equals(second.getType()));

		EQUALITY.addEquality(WeakDependency.class,
				(first, second) -> first.getTargetDependency().equals(second.getTargetDependency()));

		EQUALITY.addEquality(ConditionalDependency.class,
				(first, second) -> first.getTargetDependency().equals(second.getTargetDependency()) &&
						first.getConditionDependency().equals(second.getConditionDependency()));

		EQUALITY.addEquality(NameDependency.class, ResolvedDependency.class,
				(first, second) -> first.getName().equals(second.getModule().getName()));

		EQUALITY.addEquality(TypeDependency.class, ResolvedDependency.class,
				(first, second) -> first.getType().equals(second.getModule().getClass()));
	}

	public static boolean matches(Dependency first, Dependency second) {
		return EQUALITY.test(first, second);
	}

	public static DependencyBuilder1 builder() {
		return new DependencyBuilder1();
	}

	public static Dependency of(Class<? extends Module> type) {
		TypeDependency dependency = new TypeDependency();
		dependency.setType(type);
		return dependency;
	}

	public static Dependency of(String name) {
		NameDependency dependency = new NameDependency();
		dependency.setName(name);
		return dependency;
	}

	public static Dependency weak(String name) {
		return Dependencies.decorate(of(name), new WeakDependency());
	}

	public static List<Dependency> of(Iterable<Module> modules) {
		return Lists.newArrayList(_Iterable.transform(modules, ResolvedDependency::new));
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
			ConditionalDependency decoratedDependency = Dependencies.decorate(dependency, new ConditionalDependency());
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