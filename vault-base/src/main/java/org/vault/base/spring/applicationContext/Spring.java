package org.vault.base.spring.applicationContext;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class Spring implements ApplicationContextAware {
	private static volatile ApplicationContext applicationContext;

	public static ApplicationContext getCurrentApplicationContext() {
		if (applicationContext == null) {
			throw new RuntimeException("No static reference to application context");
		}
		return applicationContext;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		Spring.applicationContext = applicationContext;
	}
}