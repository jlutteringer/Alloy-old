package org.alloy.metal.extensibility;

import java.util.List;

import org.alloy.metal.spring.AlloyBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;

public abstract class AbstractConfigurationResolver<T, N> extends AlloyBean implements ConfigurationResolver<N> {
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