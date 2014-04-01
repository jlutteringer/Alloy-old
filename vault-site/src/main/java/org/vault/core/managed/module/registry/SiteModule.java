package org.vault.core.managed.module.registry;

import org.springframework.stereotype.Component;
import org.vault.core.module.domain.simple.SimpleModule;

@Component
public class SiteModule extends SimpleModule {
	public SiteModule() {
		this.name = "Site Module";
	}
}
