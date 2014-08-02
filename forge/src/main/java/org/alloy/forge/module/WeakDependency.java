package org.alloy.forge.module;

import org.alloy.metal.object._Object;

public class WeakDependency extends DependencyDecorator {
	public WeakDependency() {
		this.isOptional = true;
	}

	@Override
	public int hashCode() {
		return _Object.hashCode(this.getTargetDependency());
	}

	@Override
	public void setTargetDependency(Dependency targetDependency) {
		super.setTargetDependency(targetDependency);
		targetDependency.setOptional(true);
	}
}