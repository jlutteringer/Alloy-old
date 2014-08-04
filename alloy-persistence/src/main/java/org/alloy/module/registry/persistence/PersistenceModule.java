package org.alloy.module.registry.persistence;

import org.alloy.forge.module.Dependencies;
import org.alloy.forge.module.ManagedModule;
import org.alloy.module.registry.cache.CacheModule;
import org.springframework.stereotype.Component;

@Component
public class PersistenceModule extends ManagedModule {
	public PersistenceModule() {
		this.name = "alloy-persistence";
		this.friendlyName = "Persistence Module";
		this.dependencies.add(Dependencies.of(CacheModule.class));
	}
}