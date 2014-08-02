package org.alloy.forge.module;

public interface Dependency {
	public boolean isOptional();

	public void setOptional(boolean isOptional);
}