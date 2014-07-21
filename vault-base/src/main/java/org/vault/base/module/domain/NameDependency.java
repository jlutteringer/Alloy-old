package org.vault.base.module.domain;

import org.vault.base.utilities.object.VObjects;

public class NameDependency extends AbstractDependency {
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		return VObjects.hashCode(name);
	}

	@Override
	public String toString() {
		return "Name: " + name;
	}
}