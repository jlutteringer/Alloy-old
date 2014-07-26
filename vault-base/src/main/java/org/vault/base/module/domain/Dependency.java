package org.vault.base.module.domain;

public interface Dependency {
	public boolean isOptional();

	public void setOptional(boolean isOptional);
}