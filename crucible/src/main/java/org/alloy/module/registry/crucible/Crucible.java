package org.alloy.module.registry.crucible;

import org.alloy.forge.module.Dependencies;
import org.alloy.forge.module.ManagedModule;
import org.alloy.module.registry.domain.DomainModule;
import org.alloy.module.registry.log.LoggingModule;
import org.alloy.module.registry.site.SiteModule;

public class Crucible extends ManagedModule {
	public Crucible() {
		this.name = "crucible";
		this.friendlyName = "Crucible";
		this.dependencies.add(Dependencies.of(DomainModule.class), Dependencies.of(SiteModule.class), Dependencies.of(LoggingModule.class));
	}
}