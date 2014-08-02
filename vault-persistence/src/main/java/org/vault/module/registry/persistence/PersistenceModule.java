package org.vault.module.registry.persistence;

import org.alloy.forge.module.Dependencies;
import org.alloy.forge.module.ManagedModule;
import org.springframework.stereotype.Component;
import org.vault.module.registry.cache.CacheModule;

@Component
public class PersistenceModule extends ManagedModule {
	public PersistenceModule() {
		this.name = "vault-persistence";
		this.friendlyName = "Persistence Module";
		this.dependencies.add(Dependencies.of(CacheModule.class));
	}
}