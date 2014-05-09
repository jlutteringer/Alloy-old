package org.vault.module.registry.core;

import org.springframework.stereotype.Component;
import org.vault.base.module.domain.ModuleType;
import org.vault.core.module.domain.simple.SimpleModule;

@Component
public class CoreModule extends SimpleModule {
	public CoreModule() {
		this.name = "vault-core";
		this.friendlyName = "Core Module";
		this.type = ModuleType.CORE;
	}
}