package org.vault.base.module.domain;

import org.vault.base.utilities.object.VObjects;

public class WeakDependency extends DependencyDecorator {
	@Override
	public int hashCode() {
		return VObjects.hashCode(this.getTargetDependency());
	}
}
