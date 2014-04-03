package org.vault.base.utilities.configuration;

import java.io.IOException;

import org.springframework.context.ApplicationContext;
import org.vault.base.resources.stream.ResourceInputStream;

public interface ConfigurationLocation {
	public ResourceInputStream resolveResource(ApplicationContext context) throws IOException;
}