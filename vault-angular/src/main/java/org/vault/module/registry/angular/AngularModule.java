package org.vault.module.registry.angular;

import org.alloy.forge.module.ManagedModule;
import org.springframework.stereotype.Component;

@Component
public class AngularModule extends ManagedModule {
	public AngularModule() {
		this.name = "vault-angular";
		this.friendlyName = "Angular Module";
	}
}
