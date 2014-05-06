package org.vault.base.spring.beans;

public abstract class AbstractInitializingBean implements InitializingBean {
	protected int order = 0;

	@Override
	public int getOrder() {
		return order;
	}
}
