package org.alloy.module.registry.domain;

import org.alloy.forge.module.Dependencies;
import org.alloy.forge.module.ManagedModule;
import org.alloy.module.registry.persistence.PersistenceModule;
import org.springframework.stereotype.Component;

@Component
public class DomainModule extends ManagedModule {
	public DomainModule() {
		this.name = "alloy-domain";
		this.friendlyName = "Domain Module";
		this.dependencies.add(Dependencies.of(PersistenceModule.class));
	}
}