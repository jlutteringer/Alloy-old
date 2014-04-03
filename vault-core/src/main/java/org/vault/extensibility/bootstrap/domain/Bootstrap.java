package org.vault.extensibility.bootstrap.domain;

import org.springframework.context.ApplicationContext;

public interface Bootstrap<T extends ApplicationContext> {
	public T bootstrap();
}
