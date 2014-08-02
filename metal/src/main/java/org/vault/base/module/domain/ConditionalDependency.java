package org.vault.base.module.domain;

import org.alloy.metal.object._Object;

public class ConditionalDependency extends DependencyDecorator {
	private Dependency conditionDependency;

	public ConditionalDependency() {
		this.isOptional = true;
	}

	public Dependency getConditionDependency() {
		return conditionDependency;
	}

	public void setConditionDependency(Dependency conditionDependency) {
		conditionDependency.setOptional(true);
		this.conditionDependency = conditionDependency;
	}

	@Override
	public int hashCode() {
		return _Object.hashCode(this.getTargetDependency(), this.getConditionDependency());
	}
}