package org.vault.module.registry.cache;

import org.springframework.stereotype.Component;
import org.vault.core.module.domain.simple.ManagedModule;

@Component
public class CacheModule extends ManagedModule {
	public CacheModule() {
		this.name = "vault-cache";
		this.friendlyName = "Cache Module";
	}
}