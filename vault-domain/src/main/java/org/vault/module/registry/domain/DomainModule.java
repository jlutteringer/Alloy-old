package org.vault.module.registry.domain;

import org.springframework.stereotype.Component;
import org.vault.core.module.domain.simple.ManagedModule;

import com.google.common.collect.Lists;

@Component
public class DomainModule extends ManagedModule {
	public DomainModule() {
		this.name = "vault-domain";
		this.friendlyName = "Domain Module";
		this.dependencies = Lists.newArrayList("vault-persistence");
	}
}