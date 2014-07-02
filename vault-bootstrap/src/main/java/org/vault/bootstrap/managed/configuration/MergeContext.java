package org.vault.bootstrap.managed.configuration;

import org.springframework.core.io.Resource;
import org.vault.bootstrap.managed.merge.MergeManager;

public interface MergeContext {
	public default Resource getMergedResource() {
		return this.getMergeManager().getMergedResource(this.getConfigurationManager().buildConfigurationLocations());
	}

	public MergeManager getMergeManager();

	public ConfigurationManager getConfigurationManager();
}