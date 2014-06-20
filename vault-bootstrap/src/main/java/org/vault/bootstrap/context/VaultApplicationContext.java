package org.vault.bootstrap.context;

import org.springframework.context.ConfigurableApplicationContext;

public interface VaultApplicationContext extends ConfigurableApplicationContext {
	public BootstrappedContext getInitializationContext();

	public void setInitializationContext(BootstrappedContext initializationContext);
}
