package org.vault.core.module.domain.simple;

import org.vault.base.module.domain.ModuleType;

public abstract class ApplicationModule extends SimpleModule {
	public ApplicationModule() {
		name = "Application Module";
		type = ModuleType.APPLICATION;
	}
}