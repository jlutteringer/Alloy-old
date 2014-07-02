package org.vault.module.registry.persistence;

import org.springframework.stereotype.Component;
import org.vault.core.module.domain.simple.ManagedModule;

import com.google.common.collect.Lists;

@Component
public class PersistenceModule extends ManagedModule {
	public PersistenceModule() {
		this.name = "vault-persistence";
		this.friendlyName = "Persistence Module";
		this.dependencies = Lists.newArrayList("vault-cache");
	}
}