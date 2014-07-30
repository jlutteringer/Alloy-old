package org.vault.site.resource.bundle.configuration;

import java.util.List;

import org.vault.base.collections.lists.VLists;

public class BundleConfiguration {
	private String name;
	private List<BundleConfigurationComponent> components = VLists.list();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<BundleConfigurationComponent> getComponents() {
		return components;
	}

	public void setComponents(List<BundleConfigurationComponent> components) {
		this.components = components;
	}
}