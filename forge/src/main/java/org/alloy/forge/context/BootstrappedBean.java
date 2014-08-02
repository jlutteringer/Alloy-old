package org.alloy.forge.context;

public interface BootstrappedBean {
	public default String getName() {
		return this.getClass().getName();
	}
}
