package org.vault.bootstrap.managed.core.service.locator;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ServiceLocator implements ApplicationContextAware {
	private static ApplicationContext applicationContext;

	public static <T> T get(Class<T> clazz) {
		return applicationContext.getBean(clazz);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		ServiceLocator.applicationContext = applicationContext;
	}
}
