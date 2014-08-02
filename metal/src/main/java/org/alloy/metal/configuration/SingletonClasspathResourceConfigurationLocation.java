package org.alloy.metal.configuration;

import org.alloy.metal.collections.directory.Directory;
import org.alloy.metal.collections.directory._Directory;

public class SingletonClasspathResourceConfigurationLocation extends AbstractClasspathResourceConfigurationLocation {
	protected String resourceLocation;

	public void setResourceLocation(String resourceLocation) {
		this.resourceLocation = resourceLocation;
	}

	@Override
	public Directory<String, String> getResourceLocationDirectory() {
		return _Directory.newUnkeyedDirectory(resourceLocation);
	}
}