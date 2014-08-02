package org.alloy.forge.module;

import org.alloy.metal.object._Object;

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
		return _Object.hashCode(name);
	}

	@Override
	public String toString() {
		return "Name: " + name;
	}
}