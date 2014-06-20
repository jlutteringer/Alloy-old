package org.vault.core.context;

import org.springframework.core.io.Resource;
import org.vault.bootstrap.context.BootstrappedApplicationContext;
import org.vault.bootstrap.context.VaultApplicationContext;

public class XmlVaultApplicationContext extends BootstrappedApplicationContext implements VaultApplicationContext {
	public XmlVaultApplicationContext(Resource resource) {
		super(resource);
	}
}
