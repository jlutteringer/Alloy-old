package org.alloy.metal.extensibility;

import java.util.List;

public interface ConfigurationProvider<T> {
	public List<T> getConfigurations();
}