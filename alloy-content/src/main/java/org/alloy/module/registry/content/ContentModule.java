package org.alloy.module.registry.content;

import org.alloy.forge.module.Dependencies;
import org.alloy.forge.module.ManagedModule;
import org.alloy.module.registry.domain.DomainModule;
import org.alloy.module.registry.site.SiteModule;
import org.springframework.stereotype.Component;

@Component
public class ContentModule extends ManagedModule {
	public ContentModule() {
		this.name = "alloy-content";
		this.friendlyName = "Content Module";
		this.dependencies.add(Dependencies.of(DomainModule.class), Dependencies.of(SiteModule.class));
	}
}