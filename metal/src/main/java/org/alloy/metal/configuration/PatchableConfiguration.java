package org.alloy.metal.configuration;

import java.util.List;

public interface PatchableConfiguration {
	public List<ConfigurationLocation> getPatchLocations();

	public void setPatchLocations(List<ConfigurationLocation> patchLocations);
}
