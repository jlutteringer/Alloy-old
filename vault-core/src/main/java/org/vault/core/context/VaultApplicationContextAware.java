package org.vault.core.context;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.vault.bootstrap.context.VaultApplicationContext;

public interface VaultApplicationContextAware extends ApplicationContextAware {
	@Override
	public default void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.setApplicationContext((VaultApplicationContext) applicationContext);
	}

	public void setApplicationContext(VaultApplicationContext applicationContext) throws BeansException;
}
