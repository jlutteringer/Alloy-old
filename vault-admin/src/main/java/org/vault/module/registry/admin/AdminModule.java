package org.vault.module.registry.admin;

import org.springframework.stereotype.Component;
import org.vault.core.module.domain.simple.ManagedModule;

import com.google.common.collect.Lists;

@Component
public class AdminModule extends ManagedModule {
	public AdminModule() {
		this.name = "vault-admin";
		this.friendlyName = "Admin Module";
		this.dependencies = Lists.newArrayList("vault-web");
	}
}