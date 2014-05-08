package org.vault.base.utilities.configuration.classpath;

import java.io.IOException;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.vault.base.resources.stream.ResourceInputStream;

import com.google.common.base.Throwables;
import com.google.common.collect.Lists;

public class OptionalCRCLDecorator extends CRCLDecorator {
	public OptionalCRCLDecorator(ClasspathResourceConfigurationLocation configLocation) {
		super(configLocation);
	}

	@Override
	public List<ResourceInputStream> resolveResouceLocations(String resourceLocation, ApplicationContext context) {
		try {
			return super.resolveResouceLocations(resourceLocation, context);
		} catch (Exception e) {
			if (Throwables.getRootCause(e) instanceof IOException) {
				return Lists.newArrayList();
			}

			throw e;
		}
	}
}
