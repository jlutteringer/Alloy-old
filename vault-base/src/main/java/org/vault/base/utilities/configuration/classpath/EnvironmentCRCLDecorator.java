package org.vault.base.utilities.configuration.classpath;

import java.util.List;
import java.util.function.Function;

import org.springframework.context.ApplicationContext;
import org.vault.base.collections.directory.Directories;
import org.vault.base.collections.directory.Directory;
import org.vault.base.enviornment.EnvironmentType;
import org.vault.base.resources.stream.ResourceInputStream;
import org.vault.base.utilities.configuration.Configurations;
import org.vault.base.utilities.tuple.Tuple;
import org.vault.base.utilities.tuple.Tuple.Pair;

import com.google.common.collect.Lists;

public class EnvironmentCRCLDecorator extends CRCLDecorator {
	private EnvironmentType currentEnvironment;

	public EnvironmentCRCLDecorator(ClasspathResourceConfigurationLocation configLocation) {
		super(configLocation);
	}

	@Override
	public String getResourceLocation(String key) {
		for (EnvironmentType type : this.getEnvironmentTypes()) {
			if (key.equals(type.getType())) {
				return String.format(super.getResourceLocation(key), type.toString().toLowerCase());
			}
		}

		return super.getResourceLocation(key);
	}

	@Override
	protected Directory<String, ResourceInputStream> resolveResourcesIternal(ApplicationContext context, Function<String, String> getResourceLocation) {
		List<Pair<String, ResourceInputStream>> resources = Lists.newArrayList();
		for (EnvironmentType type : this.getEnvironmentTypes()) {
			resources.add(Tuple.pair(type.toString().toLowerCase(),
					Configurations.resolveClasspathResource(String.format(getResourceLocation.apply(null), type.toString().toLowerCase()), context)));
		}

		return Directories.newKeyedDirectory(resources);
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