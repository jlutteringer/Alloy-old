package org.vault.bootstrap.context;

public interface BootstrappedContextBean {
	public default String getName() {
		return this.getClass().getName();
	}
}
