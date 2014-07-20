package org.vault.base.module.domain;

public class ConditionalDependency extends DependencyDecorator {
	private Dependency conditionDependency;

	public Dependency getConditionDependency() {
		return conditionDependency;
	}

	public void setConditionDependency(Dependency conditionDependency) {
		this.conditionDependency = conditionDependency;
	}
}