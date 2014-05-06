package org.vault.base.utilities.configuration.classpath;


public abstract class CRCLDecorator extends AbstractClasspathResourceConfigurationLocation {
	protected ClasspathResourceConfigurationLocation decoratedConfigLocation;

	public CRCLDecorator(ClasspathResourceConfigurationLocation configLocation) {
		this.decoratedConfigLocation = configLocation;
	}

	@Override
	public String getResourceLocation(String key) {
		return decoratedConfigLocation.getResourceLocation(key);
	}
}