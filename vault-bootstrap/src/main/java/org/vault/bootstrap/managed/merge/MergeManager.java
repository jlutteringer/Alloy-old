package org.vault.bootstrap.managed.merge;

import java.util.List;

import org.alloy.metal.configuration.ConfigurationLocation;
import org.springframework.core.io.Resource;

public interface MergeManager {
	public Resource getMergedResource(List<ConfigurationLocation> patchLocations);
}
