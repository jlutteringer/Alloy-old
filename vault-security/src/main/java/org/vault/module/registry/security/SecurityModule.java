package org.vault.module.registry.security;

import org.springframework.stereotype.Component;
import org.vault.base.module.domain.Dependencies;
import org.vault.core.module.domain.simple.ManagedModule;
import org.vault.module.registry.user.UserModule;

@Component
public class SecurityModule extends ManagedModule {
	public SecurityModule() {
		this.name = "vault-security";
		this.friendlyName = "Security Module";
		this.dependencies.add(Dependencies.of(UserModule.class));
	}
}