package org.vault.base.module.domain;

public class Modules {
	public static boolean isApplicationModule(Module module) {
		if (ModuleType.APPLICATION.equals(module.getType())) {
			return true;
		}
		return false;
	}

	public static boolean isPresent(Class<? extends Module> moduleType) {
		// TODO
		return false;
	}

	public static boolean isEnabled(Class<? extends Module> moduleType) {
		// TODO
		return false;
	}
}