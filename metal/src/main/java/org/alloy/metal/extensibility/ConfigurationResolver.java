package org.alloy.metal.extensibility;

import java.util.List;

public interface ConfigurationResolver<T> {
	public List<T> getResolvedItems();
}