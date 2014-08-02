package org.vault.module.registry.site;

import org.alloy.forge.module.Dependencies;
import org.alloy.forge.module.ManagedModule;
import org.springframework.stereotype.Component;
import org.vault.module.registry.cache.CacheModule;

@Component
public class SiteModule extends ManagedModule {
	public SiteModule() {
		this.name = "vault-site";
		this.friendlyName = "Site Module";
		this.dependencies.add(Dependencies.of(CacheModule.class));
	}
}