package org.vault.bootstrap.service;

import org.springframework.context.ApplicationContext;

public interface Bootstrap<T extends ApplicationContext> {
	public T bootstrap();
}
