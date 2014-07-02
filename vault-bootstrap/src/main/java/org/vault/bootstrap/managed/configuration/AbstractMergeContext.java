package org.vault.bootstrap.managed.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.vault.bootstrap.managed.merge.MergeManager;

public abstract class AbstractMergeContext<T extends ConfigurationManager, N extends MergeManager> implements MergeContext {
	@Autowired
	private T configurationManager;

	@Autowired
	private N mergeManager;

	@Override
	public MergeManager getMergeManager() {
		return mergeManager;
	}

	@Override
	public ConfigurationManager getConfigurationManager() {
		return configurationManager;
	}
}