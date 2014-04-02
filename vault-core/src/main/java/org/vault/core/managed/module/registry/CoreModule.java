package org.vault.core.managed.module.registry;

import org.springframework.stereotype.Component;
import org.vault.base.collections.lists.VLists;
import org.vault.base.utilities.configuration.Configurations;
import org.vault.core.module.domain.ModuleType;
import org.vault.core.module.domain.simple.SimpleModule;

@Component
public class CoreModule extends SimpleModule {
	public CoreModule() {
		this.name = "Core Module";
		this.type = ModuleType.CORE;
		this.configurationLocations = VLists.create(
				Configurations.createLocation("vault-common-applicationContext.xml"));
	}
}