package org.vault.module.registry.persistence;

import org.springframework.stereotype.Component;
import org.vault.base.module.domain.Dependencies;
import org.vault.core.module.domain.simple.ManagedModule;
import org.vault.module.registry.cache.CacheModule;

@Component
public class PersistenceModule extends ManagedModule {
	public PersistenceModule() {
		this.name = "vault-persistence";
		this.friendlyName = "Persistence Module";
		this.dependencies.add(Dependencies.of(CacheModule.class));
	}
}