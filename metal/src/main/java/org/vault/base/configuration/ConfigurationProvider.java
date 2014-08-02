package org.vault.base.configuration;

import java.util.List;

public interface ConfigurationProvider<T> {
	public List<T> getConfigurations();
}