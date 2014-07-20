package org.vault.base.module.domain;

public class SimpleDependency implements Dependency {
	private Class<? extends Module> type;

	public Class<? extends Module> getType() {
		return type;
	}

	public void setType(Class<? extends Module> type) {
		this.type = type;
	}
}