package org.vault.base.module.domain;

public class Dependencies {
	public static DependencyBuilder1 builder() {
		return new DependencyBuilder1();
	}

	public static Dependency of(Class<? extends Module> type) {
		// TODO
		return null;
	}

	public static Dependency of(String name) {
		// TODO
		return null;
	}

	public static class DependencyBuilder1 {
		public DependencyBuilder2 of(Class<? extends Module> type) {
			// TODO
			return null;
		}

		public DependencyBuilder2 of(String name) {
			// TODO
			return null;
		}
	}

	public static class DependencyBuilder2 {
		public DependencyBuilder2 weak() {
			// TODO
			return null;
		}

		public DependencyBuilder2 conditional(Dependency includeIfPresent) {
			// TODO
			return null;
		}

		public DependencyBuilder2 conditional(Class<? extends Module> type) {
			// TODO
			return null;
		}

		public Dependency create() {
			// TODO
			return null;
		}
	}
}