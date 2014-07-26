package org.vault.base.module.domain;

public abstract class DependencyDecorator extends AbstractDependency {
	protected Dependency targetDependency;

	public Dependency getTargetDependency() {
		return targetDependency;
	}

	public void setTargetDependency(Dependency targetDependency) {
		this.targetDependency = targetDependency;
	}
}