package org.vault.module.registry.domain;

import org.springframework.stereotype.Component;
import org.vault.base.module.domain.Dependencies;
import org.vault.core.module.domain.simple.ManagedModule;
import org.vault.module.registry.persistence.PersistenceModule;

@Component
public class DomainModule extends ManagedModule {
	public DomainModule() {
		this.name = "vault-domain";
		this.friendlyName = "Domain Module";
		this.dependencies.add(Dependencies.of(PersistenceModule.class));
	}
}