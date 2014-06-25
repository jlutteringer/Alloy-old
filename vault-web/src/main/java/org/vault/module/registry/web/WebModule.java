package org.vault.module.registry.web;

import org.springframework.stereotype.Component;
import org.vault.core.module.domain.simple.ManagedModule;

import com.google.common.collect.Lists;

@Component
public class WebModule extends ManagedModule {
	public WebModule() {
		this.name = "vault-web";
		this.friendlyName = "Web Module";
		this.dependencies = Lists.newArrayList("vault-site", "vault-user", "vault-persistence");
	}
}
