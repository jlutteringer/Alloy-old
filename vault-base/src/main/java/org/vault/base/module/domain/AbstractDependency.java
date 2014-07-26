package org.vault.base.module.domain;

public class AbstractDependency implements Dependency {
	protected boolean isOptional = false;

	@Override
	public boolean equals(Object o) {
		if (o instanceof Dependency) {
			return Dependencies.matches(this, ((Dependency) o));
		}
		return false;
	}

	@Override
	public boolean isOptional() {
		return isOptional;
	}

	@Override
	public void setOptional(boolean isOptional) {
		this.isOptional = isOptional;
	}
}