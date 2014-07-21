package org.vault.base.module.domain;

import org.vault.base.utilities.object.VObjects;

public class ConditionalDependency extends DependencyDecorator {
	private Dependency conditionDependency;

	public Dependency getConditionDependency() {
		return conditionDependency;
	}

	public void setConditionDependency(Dependency conditionDependency) {
		this.conditionDependency = conditionDependency;
	}

	@Override
	public int hashCode() {
		return VObjects.hashCode(this.getTargetDependency(), this.getConditionDependency());
	}
}