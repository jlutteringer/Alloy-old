package org.vault.module.registry.admin;

import org.alloy.forge.module.Dependencies;
import org.alloy.forge.module.ManagedModule;
import org.springframework.stereotype.Component;
import org.vault.module.registry.angular.AngularModule;
import org.vault.module.registry.web.WebModule;

@Component
public class AdminModule extends ManagedModule {
	public AdminModule() {
		this.name = "vault-admin";
		this.friendlyName = "Admin Module";
		this.dependencies.add(Dependencies.of(WebModule.class), Dependencies.of(AngularModule.class));
	}
}