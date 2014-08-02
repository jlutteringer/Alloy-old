package org.alloy.forge.merge;

import org.alloy.forge.configuration.ConfigurationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;

public abstract class AbstractMergeContext<T extends ConfigurationManager, N extends MergeManager> implements MergeContext {
	@Autowired
	private T configurationManager;

	@Autowired
	private N mergeManager;

	@Override
	public Resource getMergedResource() {
		return this.getMergeManager().getMergedResource(this.getConfigurationManager().buildConfigurationLocations());
	}

	@Override
	public MergeManager getMergeManager() {
		return mergeManager;
	}

	@Override
	public ConfigurationManager getConfigurationManager() {
		return configurationManager;
	}
}