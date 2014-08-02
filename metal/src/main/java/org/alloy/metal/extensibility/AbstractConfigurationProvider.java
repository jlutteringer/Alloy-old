package org.alloy.metal.extensibility;

import java.util.List;

import org.alloy.metal.spring.AlloyBean;

import com.google.common.collect.Lists;

public abstract class AbstractConfigurationProvider<T> extends AlloyBean implements ConfigurationProvider<T> {
	@Override
	public List<T> getConfigurations() {
		List<T> configurations = Lists.newArrayList();
		this.addConfigurations(configurations);
		return configurations;
	}

	protected abstract void addConfigurations(List<T> configurations);
}