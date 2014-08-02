package org.vault.module.registry.user;

import org.alloy.forge.module.Dependencies;
import org.alloy.forge.module.ManagedModule;
import org.springframework.stereotype.Component;
import org.vault.module.registry.domain.DomainModule;
import org.vault.module.registry.persistence.PersistenceModule;

@Component
public class UserModule extends ManagedModule {
	public UserModule() {
		this.name = "vault-user";
		this.friendlyName = "User Module";
		this.dependencies.add(Dependencies.of(PersistenceModule.class), Dependencies.of(DomainModule.class));
	}
}