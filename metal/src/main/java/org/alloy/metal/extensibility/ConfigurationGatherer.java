package org.alloy.metal.extensibility;

import java.util.List;

public class ConfigurationGatherer<T> extends AbstractConfigurationResolver<T, T> {
	@Override
	protected List<T> resolveItems(List<T> configurations) {
		return configurations;
	}
}