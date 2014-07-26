package org.vault.base.module.domain;

import org.vault.base.utilities.object.VObjects;

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
		return VObjects.hashCode(this.getTargetDependency(), this.getConditionDependency());
	}
}