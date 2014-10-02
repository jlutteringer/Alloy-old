package org.alloy.module.registry.content;

import org.alloy.forge.module.ManagedModule;
import org.springframework.stereotype.Component;

@Component
public class ContentModule extends ManagedModule {
	public ContentModule() {
		this.name = "alloy-content";
		this.friendlyName = "Content Module";
	}
}