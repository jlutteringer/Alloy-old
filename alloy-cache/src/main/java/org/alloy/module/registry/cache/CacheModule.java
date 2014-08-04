package org.alloy.module.registry.cache;

import org.alloy.forge.module.ManagedModule;
import org.springframework.stereotype.Component;

@Component
public class CacheModule extends ManagedModule {
	public CacheModule() {
		this.name = "alloy-cache";
		this.friendlyName = "Cache Module";
	}
}