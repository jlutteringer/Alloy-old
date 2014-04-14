package org.vault.base.utilities.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.vault.base.resources.stream.ResourceInputStream;

import com.google.common.base.Throwables;
import com.google.common.collect.Lists;

public class ClasspathResourceConfigurationLocation extends AbstractConfigurationLocation {
	private String resourceLocation;

	public String getResourceLocation() {
		return resourceLocation;
	}

	public void setResourceLocation(String resourceLocation) {
		this.resourceLocation = resourceLocation;
	}

	@SuppressWarnings("resource")
	@Override
	public List<ResourceInputStream> resolveResources(ApplicationContext context) {
		ResourceInputStream stream;
		if (resourceLocation.startsWith("classpath")) {
			InputStream is = ClasspathResourceConfigurationLocation.class.getClassLoader()
					.getResourceAsStream(resourceLocation.substring("classpath*:".length(), resourceLocation.length()));

			stream = new ResourceInputStream(is, resourceLocation);
		} else {
			try {
				stream = ResourceInputStream.create(resourceLocation, context);
			} catch (IOException e) {
				throw Throwables.propagate(e);
			}
		}

		return Lists.newArrayList(stream);
	}
}
