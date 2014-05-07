package org.vault.base.utilities.configuration.classpath;

import java.util.List;

import com.google.common.collect.Lists;

public class ConcreteClasspathResourceConfigurationLocation extends AbstractClasspathResourceConfigurationLocation {
	private String resourceLocation;

	public void setResourceLocation(String resourceLocation) {
		this.resourceLocation = resourceLocation;
	}

	@Override
	public String getResourceLocation(String key) {
		return resourceLocation;
	}

	@Override
	public List<String> getKeys() {
		return Lists.newArrayList();
	}
}