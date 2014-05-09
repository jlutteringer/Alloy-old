package org.vault.core.context;

import org.springframework.context.ApplicationContext;
import org.vault.bootstrap.context.BootstrappedContext;

public interface VaultApplicationContext extends ApplicationContext {
	public BootstrappedContext getInitializationContext();
}
