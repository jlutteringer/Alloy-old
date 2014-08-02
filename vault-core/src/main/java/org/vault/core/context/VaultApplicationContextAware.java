package org.vault.core.context;

import org.alloy.bootstrap.context.AlloyApplicationContext;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public interface VaultApplicationContextAware extends ApplicationContextAware {
	@Override
	public default void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.setApplicationContext((AlloyApplicationContext) applicationContext);
	}

	public void setApplicationContext(AlloyApplicationContext applicationContext) throws BeansException;
}
