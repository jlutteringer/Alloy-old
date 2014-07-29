package org.vault.base.configuration;

import java.util.List;

import org.vault.base.spring.beans.VaultBean;

import com.google.common.collect.Lists;

public abstract class AbstractConfigurationProvider<T> extends VaultBean implements ConfigurationProvider<T> {
	@Override
	public List<T> getConfigurations() {
		List<T> configurations = Lists.newArrayList();
		this.addConfigurations(configurations);
		return configurations;
	}

	protected abstract void addConfigurations(List<T> configurations);
}