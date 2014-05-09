package org.vault.bootstrap.context;

import org.springframework.core.io.Resource;
import org.vault.extensibility.context.ResourceApplicationContext;

public class BootstrappedApplicationContext extends ResourceApplicationContext {
	private BootstrappedContext initializationContext;

	public BootstrappedApplicationContext(Resource resource) {
		super(resource);
	}

	public BootstrappedContext getInitializationContext() {
		return initializationContext;
	}

	public void setInitializationContext(BootstrappedContext initializationContext) {
		this.initializationContext = initializationContext;
	}
}