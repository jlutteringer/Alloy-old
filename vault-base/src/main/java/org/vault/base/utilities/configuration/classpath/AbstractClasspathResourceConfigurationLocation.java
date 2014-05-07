package org.vault.base.utilities.configuration.classpath;

import org.vault.base.utilities.configuration.AbstractConfigurationLocation;

public abstract class AbstractClasspathResourceConfigurationLocation extends AbstractConfigurationLocation implements ClasspathResourceConfigurationLocation {
	@Override
	public String toString() {
		return this.getResourceLocation(null);
	}
}
