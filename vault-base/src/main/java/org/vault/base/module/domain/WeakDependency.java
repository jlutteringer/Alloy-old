package org.vault.base.module.domain;

import org.vault.base.utilities.object.VObjects;

public class WeakDependency extends DependencyDecorator {
	public WeakDependency() {
		this.isOptional = true;
	}

	@Override
	public int hashCode() {
		return VObjects.hashCode(this.getTargetDependency());
	}

	@Override
	public void setTargetDependency(Dependency targetDependency) {
		super.setTargetDependency(targetDependency);
		targetDependency.setOptional(true);
	}
}