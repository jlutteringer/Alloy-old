package org.alloy.module.registry.user;

import org.alloy.forge.module.Dependencies;
import org.alloy.forge.module.ManagedModule;
import org.alloy.module.registry.domain.DomainModule;
import org.alloy.module.registry.persistence.PersistenceModule;
import org.springframework.stereotype.Component;

@Component
public class UserModule extends ManagedModule {
	public UserModule() {
		this.name = "alloy-user";
		this.friendlyName = "User Module";
		this.dependencies.add(Dependencies.of(PersistenceModule.class), Dependencies.of(DomainModule.class));
	}
}