package org.vault.module.registry.site;

import org.springframework.stereotype.Component;
import org.vault.core.module.domain.simple.ManagedModule;

import com.google.common.collect.Lists;

@Component
public class SiteModule extends ManagedModule {
	public SiteModule() {
		this.name = "vault-site";
		this.friendlyName = "Site Module";
		this.dependencies = Lists.newArrayList("vault-cache");
	}
}