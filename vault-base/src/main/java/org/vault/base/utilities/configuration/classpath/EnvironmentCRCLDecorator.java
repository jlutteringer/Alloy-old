package org.vault.base.utilities.configuration.classpath;

import java.util.List;

import org.vault.base.enviornment.EnvironmentType;

import com.google.common.collect.Lists;

public class EnvironmentCRCLDecorator extends CRCLDecorator {
	private EnvironmentType currentEnvironment;

	public EnvironmentCRCLDecorator(ClasspathResourceConfigurationLocation configLocation) {
		super(configLocation);
	}

	@Override
	public String getResourceLocation(String key) {
		if (key != null) {
			for (EnvironmentType type : this.getEnvironmentTypes()) {
				if (key.equals(type.getType())) {
					return String.format(super.getResourceLocation(key), type.toString().toLowerCase());
				}
			}
		}

		return super.getResourceLocation(key);
	}

	@Override
	public List<String> getKeys() {
		List<String> keys = Lists.newArrayList();

		for (EnvironmentType type : this.getEnvironmentTypes()) {
			keys.add(type.getType());
		}

		keys.addAll(decoratedConfigLocation.getKeys());
		return keys;
	}

	private List<EnvironmentType> getEnvironmentTypes() {
		return Lists.newArrayList(EnvironmentType.SHARED, currentEnvironment);
	}

	public EnvironmentType getCurrentEnvironment() {
		return currentEnvironment;
	}

	public void setCurrentEnvironment(EnvironmentType currentEnvironment) {
		this.currentEnvironment = currentEnvironment;
	}
}