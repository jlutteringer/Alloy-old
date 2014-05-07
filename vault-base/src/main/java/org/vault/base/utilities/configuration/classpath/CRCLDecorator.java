package org.vault.base.utilities.configuration.classpath;

import org.springframework.context.ApplicationContext;
import org.vault.base.collections.directory.Directory;
import org.vault.base.resources.stream.ResourceInputStream;

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
	public Directory<String, ResourceInputStream> resolveResources(ApplicationContext context) {
		return decoratedConfigLocation.resolveResources(context);
	}
}