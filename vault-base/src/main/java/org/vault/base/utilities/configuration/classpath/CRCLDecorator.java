package org.vault.base.utilities.configuration.classpath;

import java.util.List;

public abstract class CRCLDecorator extends AbstractClasspathResourceConfigurationLocation {
	protected ClasspathResourceConfigurationLocation decoratedConfigLocation;

	public CRCLDecorator(ClasspathResourceConfigurationLocation configLocation) {
		this.decoratedConfigLocation = configLocation;
	}

	@Override
	public String getResourceLocation(String key) {
		return decoratedConfigLocation.getResourceLocation(key);
	}

	@Override
	public List<String> getKeys() {
		return decoratedConfigLocation.getKeys();
	}
}