package org.alloy.site.resource.bundle.configuration;

import java.util.List;

import org.alloy.metal.collections.lists._List;

public class ResourceConfiguration {
	private String name;
	private List<ResourceConfigurationComponents> components = _List.list();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ResourceConfigurationComponents> getComponents() {
		return components;
	}

	public void setComponents(List<ResourceConfigurationComponents> components) {
		this.components = components;
	}
}