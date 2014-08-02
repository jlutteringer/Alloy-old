package org.alloy.forge.module;

public class Modules {
	public static boolean isApplicationModule(Module module) {
		if (ModuleType.APPLICATION.equals(module.getType())) {
			return true;
		}
		return false;
	}
}