package org.vault.module.registry.user;

import org.springframework.stereotype.Component;
import org.vault.core.module.domain.simple.ManagedModule;

import com.google.common.collect.Lists;

@Component
public class UserModule extends ManagedModule {
	public UserModule() {
		this.name = "vault-user";
		this.friendlyName = "User Module";
		this.dependencies = Lists.newArrayList("vault-persistence", "vault-domain");
	}
}