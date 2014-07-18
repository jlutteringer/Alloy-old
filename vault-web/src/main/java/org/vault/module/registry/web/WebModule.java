package org.vault.module.registry.web;

import org.springframework.stereotype.Component;
import org.vault.base.module.domain.Dependencies;
import org.vault.core.module.domain.simple.ManagedModule;
import org.vault.module.registry.security.SecurityModule;
import org.vault.module.registry.site.SiteModule;
import org.vault.module.registry.site.security.SiteSecurityModule;

@Component
public class WebModule extends ManagedModule {
	public WebModule() {
		this.name = "vault-web";
		this.friendlyName = "Web Module";
		this.dependencies.add(Dependencies.of(SiteSecurityModule.class), Dependencies.of(SecurityModule.class), Dependencies.of(SiteModule.class));
	}
}