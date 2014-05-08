package org.vault.base.utilities.configuration.classpath;

import org.vault.base.collections.directory.Directories;
import org.vault.base.collections.directory.Directory;

public class SingletonClasspathResourceConfigurationLocation extends AbstractClasspathResourceConfigurationLocation {
	protected String resourceLocation;

	public void setResourceLocation(String resourceLocation) {
		this.resourceLocation = resourceLocation;
	}

	@Override
	public Directory<String, String> getResourceLocationDirectory() {
		return Directories.newUnkeyedDirectory(resourceLocation);
	}
}