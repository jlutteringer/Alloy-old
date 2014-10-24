package org.alloy.module.registry.log;

import org.alloy.forge.module.Dependencies;
import org.alloy.forge.module.ManagedModule;
import org.alloy.module.registry.domain.DomainModule;
import org.alloy.module.registry.site.SiteModule;
import org.springframework.stereotype.Component;

@Component
public class LoggingModule extends ManagedModule {
	public LoggingModule() {
		this.name = "alloy-log";
		this.friendlyName = "Logging Module";
		this.dependencies.add(Dependencies.of(DomainModule.class), Dependencies.of(SiteModule.class));
	}
}