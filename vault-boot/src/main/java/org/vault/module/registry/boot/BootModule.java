package org.vault.module.registry.boot;

import org.alloy.forge.module.Dependencies;
import org.alloy.forge.module.ManagedModule;
import org.springframework.stereotype.Component;
import org.vault.module.registry.site.boot.SiteBootModule;

@Component
public class BootModule extends ManagedModule {
	public BootModule() {
		this.name = "vault-boot";
		this.friendlyName = "Boot Module";
		this.dependencies.add(Dependencies.builder().of(SiteBootModule.class).conditional("vault-site").create());
	}
}