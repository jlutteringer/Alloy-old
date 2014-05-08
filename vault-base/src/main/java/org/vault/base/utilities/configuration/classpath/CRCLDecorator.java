package org.vault.base.utilities.configuration.classpath;

import org.vault.base.collections.directory.Directory;

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