package org.vault.base.module.domain;

public class AbstractDependency implements Dependency {
	@Override
	public boolean equals(Object o) {
		if (o instanceof Dependency) {
			return Dependencies.equals(this, ((Dependency) o));
		}
		return false;
	}
}
