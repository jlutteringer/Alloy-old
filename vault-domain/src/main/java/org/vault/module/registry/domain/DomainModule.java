package org.vault.module.registry.domain;

import org.alloy.forge.module.Dependencies;
import org.alloy.forge.module.ManagedModule;
import org.springframework.stereotype.Component;
import org.vault.module.registry.persistence.PersistenceModule;

@Component
public class DomainModule extends ManagedModule {
	public DomainModule() {
		this.name = "vault-domain";
		this.friendlyName = "Domain Module";
		this.dependencies.add(Dependencies.of(PersistenceModule.class));
	}
}