package org.vault.core.module.domain.simple;

import java.util.List;

import org.vault.base.module.domain.Module;
import org.vault.base.module.domain.ModuleType;
import org.vault.base.utilities.configuration.ConfigurationLocation;
import org.vault.base.utilities.configuration.classpath.ClasspathResourceConfigurationLocation;
import org.vault.base.utilities.configuration.classpath.ModuleRelativeCRCLDecorator;

import com.google.common.collect.Lists;

public class SimpleModule implements Module {
	protected String name;
	protected String friendlyName;
	protected ModuleType type = ModuleType.MODULE;

	protected List<String> dependencies = Lists.newArrayList();
	protected List<ConfigurationLocation> configurationLocations = Lists.newArrayList();
	protected List<ConfigurationLocation> log4jConfigurationLocations = Lists.newArrayList();
	protected List<ConfigurationLocation> propertyConfigurationLocations = Lists.newArrayList();

	@Override
	public String getName() {
		return name;
	}

	@Override
	public ModuleType getType() {
		return type;
	}

	@Override
	public List<ConfigurationLocation> getBaseConfigurationLocations() {
		return configurationLocations;
	}

	@Override
	public List<ConfigurationLocation> getModuleConfigurationLocations() {
		List<ConfigurationLocation> decoratedConfigurations = Lists.newArrayList();
		for (ConfigurationLocation configLocation : configurationLocations) {
			if (configLocation instanceof ClasspathResourceConfigurationLocation) {
				decoratedConfigurations.add(new ModuleRelativeCRCLDecorator((ClasspathResourceConfigurationLocation) configLocation, this));
			}
			else {
				decoratedConfigurations.add(configLocation);
			}
		}

		return decoratedConfigurations;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setType(ModuleType type) {
		this.type = type;
	}

	public void setConfigurationLocations(List<ConfigurationLocation> configurationLocations) {
		this.configurationLocations = configurationLocations;
	}

	@Override
	public List<String> getDependencies() {
		return dependencies;
	}

	public void setDependencies(List<String> dependencies) {
		this.dependencies = dependencies;
	}

	@Override
	public String toString() {
		return name + " " + type;
	}

	@Override
	public List<ConfigurationLocation> getLog4jConfigurationLocations() {
		return log4jConfigurationLocations;
	}

	@Override
	public String getFriendlyName() {
		return friendlyName;
	}

	@Override
	public List<ConfigurationLocation> getPropertyConfigurationLocations() {
		return propertyConfigurationLocations;
	}
}
