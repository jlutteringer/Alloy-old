package org.vault.base.utilities.configuration.classpath;

public class BaseClasspathResourceConfigurationLocation extends AbstractClasspathResourceConfigurationLocation {
	private String resourceLocation;

	public void setResourceLocation(String resourceLocation) {
		this.resourceLocation = resourceLocation;
	}

	@Override
	public String getResourceLocation(String key) {
		return resourceLocation;
	}
}