package org.alloy.module.registry.web;

import org.alloy.forge.module.Dependencies;
import org.alloy.forge.module.ManagedModule;
import org.alloy.module.registry.content.ContentModule;
import org.alloy.module.registry.security.SecurityModule;
import org.alloy.module.registry.site.SiteModule;
import org.alloy.module.registry.site.security.SiteSecurityModule;
import org.springframework.stereotype.Component;

@Component
public class WebModule extends ManagedModule {
	public WebModule() {
		this.name = "alloy-web";
		this.friendlyName = "Web Module";
		this.dependencies.add(Dependencies.of(SiteSecurityModule.class), Dependencies.of(SecurityModule.class), Dependencies.of(SiteModule.class), Dependencies.of(ContentModule.class));
	}
}