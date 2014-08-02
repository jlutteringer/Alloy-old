package org.vault.base.configuration;

import java.util.List;

public interface ConfigurationResolver<T> {
	public List<T> getResolvedItems();
}