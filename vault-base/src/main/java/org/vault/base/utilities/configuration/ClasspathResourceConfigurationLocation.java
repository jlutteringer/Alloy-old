package org.vault.base.utilities.configuration;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.vault.base.resources.stream.ResourceInputStream;

public class ClasspathResourceConfigurationLocation extends AbstractConfigurationLocation {
	private String resourceLocation;

	public String getResourceLocation() {
		return resourceLocation;
	}

	public void setResourceLocation(String resourceLocation) {
		this.resourceLocation = resourceLocation;
	}

	@Override
	public ResourceInputStream resolveResource(ApplicationContext context) throws IOException {
		ResourceInputStream stream;
		if (resourceLocation.startsWith("classpath")) {
			InputStream is = ClasspathResourceConfigurationLocation.class.getClassLoader()
					.getResourceAsStream(resourceLocation.substring("classpath*:".length(), resourceLocation.length()));

			stream = new ResourceInputStream(is, resourceLocation);
		} else {
			Resource resource = context.getResource(resourceLocation);
			stream = new ResourceInputStream(resource.getInputStream(), resourceLocation);
		}

		return stream;
	}
}
