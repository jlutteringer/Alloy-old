package org.alloy.metal.configuration;

import org.alloy.metal.collections.directory.Directory;

public abstract class CRCLDecorator extends AbstractClasspathResourceConfigurationLocation {
	protected ClasspathResourceConfigurationLocation decoratedConfigLocation;

	public CRCLDecorator(ClasspathResourceConfigurationLocation configLocation) {
		this.decoratedConfigLocation = configLocation;
	}

	@Override
	public Directory<String, String> getResourceLocationDirectory() {
		return decoratedConfigLocation.getResourceLocationDirectory();
	}
}