package org.alloy.forge.managed.module;

import java.util.Collection;

import org.alloy.forge.module.ApplicationModule;
import org.alloy.forge.module.CoreModule;
import org.alloy.forge.module.Dependency;
import org.alloy.forge.module.Module;

public interface ModuleContext {
	public Module getModule(Class<? extends Module> type);

	public Module getModule(String name);

	public CoreModule getCoreModule();

	public ApplicationModule getApplicationModule();

	public Collection<Module> getModules();

	public Collection<Dependency> getDependencies(Module module);
}