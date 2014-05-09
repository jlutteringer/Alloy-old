package org.vault.module.registry.persistence;

import org.springframework.stereotype.Component;
import org.vault.core.module.domain.simple.SimpleModule;

@Component
public class PersistenceModule extends SimpleModule {
	public PersistenceModule() {
		this.name = "vault-persistence";
		this.friendlyName = "Persistence Module";
	}
}