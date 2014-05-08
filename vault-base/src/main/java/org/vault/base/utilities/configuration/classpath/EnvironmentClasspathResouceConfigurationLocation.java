package org.vault.base.utilities.configuration.classpath;

import java.util.List;

import org.vault.base.collections.directory.Directories;
import org.vault.base.collections.directory.Directory;
import org.vault.base.enviornment.EnvironmentType;

import com.google.common.collect.Lists;

public class EnvironmentClasspathResouceConfigurationLocation extends SingletonClasspathResourceConfigurationLocation {
	private EnvironmentType currentEnvironment;

	private List<EnvironmentType> getEnvironmentTypes() {
		return Lists.newArrayList(EnvironmentType.SHARED, currentEnvironment);
	}

	@Override
	public Directory<String, String> getResourceLocationDirectory() {
		Directory<String, String> directory = Directories.newDirectory();
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