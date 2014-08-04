package org.alloy.forge.module;



public abstract class CoreModule extends ManagedModule {
	public CoreModule() {
		this.name = "alloy-core";
		this.friendlyName = "Core Module";
		this.type = ModuleType.CORE;
	}
}