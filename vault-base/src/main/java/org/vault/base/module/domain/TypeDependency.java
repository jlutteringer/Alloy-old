package org.vault.base.module.domain;

import org.vault.base.utilities.object.VObjects;

public class TypeDependency extends AbstractDependency {
	private Class<? extends Module> type;

	public Class<? extends Module> getType() {
		return type;
	}

	public void setType(Class<? extends Module> type) {
		this.type = type;
	}

	@Override
	public int hashCode() {
		return VObjects.hashCode(type);
	}

	@Override
	public String toString() {
		return "Type: " + type.toString();
	}
}