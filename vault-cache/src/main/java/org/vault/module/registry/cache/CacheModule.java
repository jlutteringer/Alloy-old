package org.vault.module.registry.cache;

import org.alloy.forge.module.ManagedModule;
import org.springframework.stereotype.Component;

@Component
public class CacheModule extends ManagedModule {
	public CacheModule() {
		this.name = "vault-cache";
		this.friendlyName = "Cache Module";
	}
}