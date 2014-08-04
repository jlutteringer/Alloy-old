package org.alloy.module.registry.admin;

import org.alloy.forge.module.Dependencies;
import org.alloy.forge.module.ManagedModule;
import org.alloy.module.registry.angular.AngularModule;
import org.alloy.module.registry.web.WebModule;
import org.springframework.stereotype.Component;

@Component
public class AdminModule extends ManagedModule {
	public AdminModule() {
		this.name = "alloy-admin";
		this.friendlyName = "Admin Module";
		this.dependencies.add(Dependencies.of(WebModule.class), Dependencies.of(AngularModule.class));
	}
}