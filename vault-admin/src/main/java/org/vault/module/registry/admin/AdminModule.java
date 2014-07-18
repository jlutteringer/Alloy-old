package org.vault.module.registry.admin;

import org.springframework.stereotype.Component;
import org.vault.base.module.domain.Dependencies;
import org.vault.core.module.domain.simple.ManagedModule;
import org.vault.module.registry.web.WebModule;

@Component
public class AdminModule extends ManagedModule {
	public AdminModule() {
		this.name = "vault-admin";
		this.friendlyName = "Admin Module";
		this.dependencies.add(Dependencies.of(WebModule.class));
	}
}