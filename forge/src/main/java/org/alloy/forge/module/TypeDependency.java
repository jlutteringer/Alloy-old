package org.alloy.forge.module;

import org.alloy.metal.object._Object;

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
		return _Object.hashCode(type);
	}

	@Override
	public String toString() {
		return "Type: " + type.toString();
	}
}