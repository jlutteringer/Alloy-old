package org.alloy.module.registry.boot;

import org.alloy.forge.module.Dependencies;
import org.alloy.forge.module.ManagedModule;
import org.alloy.module.registry.site.boot.SiteBootModule;
import org.springframework.stereotype.Component;

@Component
public class BootModule extends ManagedModule {
	public BootModule() {
		this.name = "alloy-boot";
		this.friendlyName = "Boot Module";
		this.dependencies.add(Dependencies.builder().of(SiteBootModule.class).conditional("alloy-site").create());
	}
}