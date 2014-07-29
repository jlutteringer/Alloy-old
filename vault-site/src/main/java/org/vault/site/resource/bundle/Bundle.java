package org.vault.site.resource.bundle;

import java.util.List;

import com.google.common.collect.Lists;

public class Bundle {
	private String bundleName;
	private List<String> bundleResourceLocations = Lists.newArrayList();

	public String getBundleName() {
		return bundleName;
	}

	public void setBundleName(String bundleName) {
		this.bundleName = bundleName;
	}

	public List<String> getBundleResourceLocations() {
		return bundleResourceLocations;
	}

	public void setBundleResourceLocations(List<String> bundleResourceLocations) {
		this.bundleResourceLocations = bundleResourceLocations;
	}
}
