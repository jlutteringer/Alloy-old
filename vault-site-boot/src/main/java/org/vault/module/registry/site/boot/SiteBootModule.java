package org.vault.module.registry.site.boot;

import org.alloy.forge.module.Dependencies;
import org.alloy.forge.module.ManagedModule;
import org.alloy.forge.module.ModuleType;
import org.springframework.stereotype.Component;

@Component
public class SiteBootModule extends ManagedModule {
	public SiteBootModule() {
		this.name = "vault-site-boot";
		this.friendlyName = "Site Boot Patch";
		this.type = ModuleType.PATCH;
		this.dependencies.add(Dependencies.of("vault-site"));
	}
}