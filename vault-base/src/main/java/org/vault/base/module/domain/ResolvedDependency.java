package org.vault.base.module.domain;

public class ResolvedDependency implements Dependency {
	private Module module;

	public ResolvedDependency(Module module) {
		this.module = module;
	}

	public Module getModule() {
		return module;
	}
}
