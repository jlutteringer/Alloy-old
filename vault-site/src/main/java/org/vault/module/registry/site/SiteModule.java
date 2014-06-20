package org.vault.module.registry.site;

import org.springframework.stereotype.Component;
import org.vault.core.module.domain.simple.ManagedModule;

@Component
public class SiteModule extends ManagedModule {
	public SiteModule() {
		this.name = "vault-site";
		this.friendlyName = "Site Module";
	}
}