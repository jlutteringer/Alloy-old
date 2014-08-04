package org.alloy.module.registry.security;

import org.alloy.forge.module.Dependencies;
import org.alloy.forge.module.ManagedModule;
import org.alloy.module.registry.user.UserModule;
import org.springframework.stereotype.Component;

@Component
public class SecurityModule extends ManagedModule {
	public SecurityModule() {
		this.name = "alloy-security";
		this.friendlyName = "Security Module";
		this.dependencies.add(Dependencies.of(UserModule.class));
	}
}