package org.vault.bootstrap.managed.merge;

import java.util.List;

import org.springframework.core.io.Resource;
import org.vault.base.utilities.configuration.ConfigurationLocation;

public interface MergeManager {
	public Resource getMergedResource(List<ConfigurationLocation> patchLocations);
}
