package org.vault.base.configuration;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.vault.base.spring.beans.VaultBean;

import com.google.common.collect.Lists;

public abstract class AbstractConfigurationResolver<T, N> extends VaultBean implements ConfigurationResolver<N> {
	@Autowired(required = false)
	private List<ConfigurationProvider<T>> configurationProviders = Lists.newArrayList();

	@Override
	public List<N> getResolvedItems() {
		List<T> configurations = Lists.newArrayList();
		configurationProviders.forEach((provider) -> configurations.addAll(provider.getConfigurations()));
		return this.resolveItems(configurations);
	}

	protected abstract List<N> resolveItems(List<T> configurations);
}