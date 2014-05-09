package org.vault.core.context;

import org.springframework.core.io.Resource;
import org.vault.bootstrap.context.BootstrappedApplicationContext;

public class VaultApplicationContextImpl extends BootstrappedApplicationContext implements VaultApplicationContext {
	public VaultApplicationContextImpl(Resource resource) {
		super(resource);
	}
}
