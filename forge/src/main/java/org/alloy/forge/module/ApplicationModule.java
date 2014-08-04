package org.alloy.forge.module;



public abstract class ApplicationModule extends SimpleModule {
	public ApplicationModule() {
		name = "Application Module";
		type = ModuleType.APPLICATION;
	}
}