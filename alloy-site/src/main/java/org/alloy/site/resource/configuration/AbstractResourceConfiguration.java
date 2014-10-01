package org.alloy.site.resource.configuration;

import java.util.List;

import com.google.common.collect.Lists;

public class AbstractResourceConfiguration implements ResourceConfiguration {
	private List<String> resourceLocations = Lists.newArrayList();

	public List<String> getResourceLocations() {
		return resourceLocations;
	}

	public void setResourceLocations(List<String> resourceLocations) {
		this.resourceLocations = resourceLocations;
	}
}
