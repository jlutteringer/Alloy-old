package org.vault.bootstrap.managed.properties.configuration;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vault.base.application.ApplicationMetaData;
import org.vault.base.module.domain.Module;
import org.vault.base.module.domain.PrimaryModuleFacet;
import org.vault.base.utilities.configuration.ConfigurationLocation;
import org.vault.base.utilities.configuration.Configurations;
import org.vault.base.utilities.constants.VConfigurationFileConstants;
import org.vault.bootstrap.context.BootstrappedContextBean;
import org.vault.bootstrap.managed.configuration.ConfigurationManager;

import com.google.common.collect.Lists;

@Service
public class PropertiesConfigurationManager extends ConfigurationManager implements BootstrappedContextBean {
	@Autowired
	private ApplicationMetaData application;

	@Override
	protected List<ConfigurationLocation> getSpecificConfigurationLocations(Module module) {
		return module.getFacet(PrimaryModuleFacet.class).getPropertyConfigurationLocations();
	}

	@Override
	protected List<ConfigurationLocation> getDefaultConfigurationLocations(Module module) {
		List<ConfigurationLocation> locations = Lists.newArrayList();

		locations.add(Configurations.optional(Configurations.moduleRelative(
				Configurations.createEnvironmentLocation(VConfigurationFileConstants.PROPERTIES_FILE_STRUCTURE, application.getEnvironment().getType()), module)));

		locations.add(Configurations.optional(Configurations.moduleRelative(
				Configurations.createEnvironmentLocation(
						VConfigurationFileConstants.PROPERTIES_DIRECTORY + VConfigurationFileConstants.PROPERTIES_FILE_STRUCTURE, application.getEnvironment().getType()), module)));

		return locations;
	}
}