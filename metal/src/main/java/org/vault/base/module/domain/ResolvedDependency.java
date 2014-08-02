package org.vault.base.module.domain;

import org.alloy.metal.object._Object;

public class ResolvedDependency extends AbstractDependency {
	private Module module;

	public ResolvedDependency(Module module) {
		this.module = module;
	}

	public Module getModule() {
		return module;
	}

	@Override
	public int hashCode() {
		return _Object.hashCode(module.getClass());
	}

	@Override
	public String toString() {
		return "Resolved: " + module.toString();
	}
}