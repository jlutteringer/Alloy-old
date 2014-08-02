package org.alloy.metal.configuration;

import java.util.List;

import org.alloy.metal.collections.directory.Directory;
import org.alloy.metal.collections.directory._Directory;
import org.alloy.metal.environment.EnvironmentType;

import com.google.common.collect.Lists;

public class EnvironmentClasspathResouceConfigurationLocation extends SingletonClasspathResourceConfigurationLocation {
	private EnvironmentType currentEnvironment;

	private List<EnvironmentType> getEnvironmentTypes() {
		return Lists.newArrayList(EnvironmentType.SHARED, currentEnvironment);
	}

	@Override
	public Directory<String, String> getResourceLocationDirectory() {
		Directory<String, String> directory = _Directory.newDirectory();
		for (EnvironmentType type : this.getEnvironmentTypes()) {
			directory.put(type.getType().toLowerCase(), String.format(resourceLocation, type.getType().toLowerCase()));
		}

		return directory;
	}

	public EnvironmentType getCurrentEnvironment() {
		return currentEnvironment;
	}

	public void setCurrentEnvironment(EnvironmentType currentEnvironment) {
		this.currentEnvironment = currentEnvironment;
	}
}