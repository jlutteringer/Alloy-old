package org.vault.extensibility;

import java.util.List;

import org.vault.base.utilities.configuration.ConfigurationLocation;

public interface PatchableConfiguration {
	public List<ConfigurationLocation> getPatchLocations();

	public void setPatchLocations(List<ConfigurationLocation> patchLocations);
}
