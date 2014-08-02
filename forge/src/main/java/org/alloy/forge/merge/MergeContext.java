package org.alloy.forge.merge;

import org.alloy.forge.configuration.ConfigurationManager;
import org.springframework.core.io.Resource;

public interface MergeContext {
	public Resource getMergedResource();

	public MergeManager getMergeManager();

	public ConfigurationManager getConfigurationManager();
}