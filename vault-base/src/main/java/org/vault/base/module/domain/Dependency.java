package org.vault.base.module.domain;

public interface Dependency {
	public boolean hasDependency();

	public Class<? extends Module> getDependency();
}