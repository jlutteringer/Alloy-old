package org.vault.module.registry.angular;

import org.springframework.stereotype.Component;
import org.vault.core.module.domain.simple.ManagedModule;

@Component
public class AngularModule extends ManagedModule {
	public AngularModule() {
		this.name = "vault-angular";
		this.friendlyName = "Angular Module";
	}
}
