package org.alloy.module.registry.site;

import org.alloy.forge.module.Dependencies;
import org.alloy.forge.module.ManagedModule;
import org.alloy.module.registry.cache.CacheModule;
import org.springframework.stereotype.Component;

@Component
public class SiteModule extends ManagedModule {
	public SiteModule() {
		this.name = "alloy-site";
		this.friendlyName = "Site Module";
		this.dependencies.add(Dependencies.of(CacheModule.class));
	}
}