package org.vault.module.registry.site.boot;

import org.springframework.stereotype.Component;
import org.vault.base.module.domain.Dependencies;
import org.vault.core.module.domain.simple.ManagedModule;
import org.vault.module.registry.site.SiteModule;

@Component
public class SiteBootModule extends ManagedModule {
	public SiteBootModule() {
		this.name = "vault-site-boot";
		this.friendlyName = "Site Boot Patch";
		this.dependencies.add(Dependencies.of(SiteModule.class));
	}
}