package org.vault.base.spring.beans;

public abstract class AbstractInitializingBean implements VaultInitializingBean {
	protected int order = 0;

	@Override
	public int getOrder() {
		return order;
	}
}
