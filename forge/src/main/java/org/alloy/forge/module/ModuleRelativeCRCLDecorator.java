package org.alloy.forge.module;

import org.alloy.metal.collections.directory.Directory;
import org.alloy.metal.collections.directory.DirectoryEntry;
import org.alloy.metal.configuration.CRCLDecorator;
import org.alloy.metal.configuration.ClasspathResourceConfigurationLocation;

public class ModuleRelativeCRCLDecorator extends CRCLDecorator {
	private Module module;

	public ModuleRelativeCRCLDecorator(ClasspathResourceConfigurationLocation configLocation, Module module) {
		super(configLocation);
		this.module = module;
	}

	@Override
	public Directory<String, String> getResourceLocationDirectory() {
		Directory<String, String> directory = super.getResourceLocationDirectory();
		for (DirectoryEntry<String, String> entry : directory.getEntries()) {
			if (!_Module.isApplicationModule(module)) {
				entry.setValue(module.getName() + "/" + entry.getValue());
			}
		}

		return directory;
	}

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}
}
