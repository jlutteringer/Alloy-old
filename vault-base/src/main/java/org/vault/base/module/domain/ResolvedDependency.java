package org.vault.base.module.domain;

import org.vault.base.utilities.object.VObjects;

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
		return VObjects.hashCode(module.getClass());
	}

	@Override
	public String toString() {
		return "Resolved: " + module.toString();
	}
}