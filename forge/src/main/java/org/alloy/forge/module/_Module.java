package org.alloy.forge.module;

import org.alloy.metal.configuration.ClasspathResourceConfigurationLocation;

public class _Module {
	public static ClasspathResourceConfigurationLocation moduleRelative(ClasspathResourceConfigurationLocation location, Module module) {
		return new ModuleRelativeCRCLDecorator(location, module);
	}

	public static boolean isApplicationModule(Module module) {
		if (ModuleType.APPLICATION.equals(module.getType())) {
			return true;
		}
		return false;
	}
}