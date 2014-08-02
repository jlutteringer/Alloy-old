package org.vault.module.registry.security;

import org.alloy.forge.module.Dependencies;
import org.alloy.forge.module.ManagedModule;
import org.springframework.stereotype.Component;
import org.vault.module.registry.user.UserModule;

@Component
public class SecurityModule extends ManagedModule {
	public SecurityModule() {
		this.name = "vault-security";
		this.friendlyName = "Security Module";
		this.dependencies.add(Dependencies.of(UserModule.class));
	}
}