package org.alloy.module.registry.site.security;

import org.alloy.forge.module.Dependencies;
import org.alloy.forge.module.ManagedModule;
import org.alloy.module.registry.security.SecurityModule;
import org.alloy.module.registry.site.SiteModule;
import org.springframework.stereotype.Component;

@Component
public class SiteSecurityModule extends ManagedModule {
	public SiteSecurityModule() {
		this.name = "alloy-site-security";
		this.friendlyName = "Site Security Module";

		this.dependencies
				.add(Dependencies.builder().of(SecurityModule.class)
						.weak()
						.create(),
						Dependencies.builder().of(SiteModule.class)
								.weak()
								.create());
	}
}