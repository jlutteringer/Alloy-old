package org.alloy.forge.module;

public abstract class DependencyDecorator extends AbstractDependency {
	protected Dependency targetDependency;

	public Dependency getTargetDependency() {
		return targetDependency;
	}

	public void setTargetDependency(Dependency targetDependency) {
		this.targetDependency = targetDependency;
	}
}