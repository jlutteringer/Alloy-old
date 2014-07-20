package org.vault.bootstrap.managed.core;

import java.util.Collection;

import org.vault.base.module.domain.Dependency;
import org.vault.base.module.domain.Module;
import org.vault.core.module.domain.simple.ApplicationModule;
import org.vault.module.registry.core.CoreModule;

public interface ModuleContext {
	public Module getModule(Class<? extends Module> type);

	public Module getModule(String name);

	public CoreModule getCoreModule();

	public ApplicationModule getApplicationModule();

	public Collection<Module> getModules();

	public Collection<Dependency> getDependencies(Module module);
}