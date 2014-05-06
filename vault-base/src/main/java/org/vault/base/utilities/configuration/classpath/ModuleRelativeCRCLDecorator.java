package org.vault.base.utilities.configuration.classpath;

import org.vault.base.module.domain.Module;

public class ModuleRelativeCRCLDecorator extends CRCLDecorator {
	private Module module;

	public ModuleRelativeCRCLDecorator(ClasspathResourceConfigurationLocation configLocation, Module module) {
		super(configLocation);
		this.module = module;
	}

	@Override
	public String getResourceLocation(String key) {
		return module.getName() + "/" + super.getResourceLocation(key);
	}

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}
}
