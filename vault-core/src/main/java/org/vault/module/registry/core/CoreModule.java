package org.vault.module.registry.core;

import org.springframework.stereotype.Component;
import org.vault.base.module.domain.ModuleType;
import org.vault.core.module.domain.simple.ManagedModule;

@Component
public class CoreModule extends ManagedModule {
	public CoreModule() {
		this.name = "vault-core";
		this.friendlyName = "Core Module";
		this.type = ModuleType.CORE;
	}
}