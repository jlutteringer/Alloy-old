package org.vault.bootstrap.managed.context.configuration;

import java.util.List;

import org.springframework.stereotype.Service;
import org.vault.base.module.domain.Module;
import org.vault.base.module.domain.PrimaryModuleFacet;
import org.vault.base.utilities.configuration.ConfigurationLocation;
import org.vault.base.utilities.configuration.Configurations;
import org.vault.base.utilities.constants.VConfigurationFileConstants;
import org.vault.bootstrap.managed.configuration.ConfigurationManager;

import com.google.common.collect.Lists;

@Service
public class ContextConfigurationManager extends ConfigurationManager {
	@Override
	protected List<ConfigurationLocation> getSpecificConfigurationLocations(Module module) {
		return module.getFacet(PrimaryModuleFacet.class).getConfigurationLocations();
	}

	@Override
	protected List<ConfigurationLocation> getDefaultConfigurationLocations(Module module) {
		List<ConfigurationLocation> locations = Lists.newArrayList();

		locations.add(Configurations.optional(Configurations.moduleRelative(
				Configurations.createClasspathLocation("*-applicationContext.xml"), module)));

		locations.add(Configurations.optional(Configurations.moduleRelative(
				Configurations.createClasspathLocation(VConfigurationFileConstants.CONTEXT_RESOURCE_DIRECTORY + "*-applicationContext.xml"), module)));

		return locations;
	}
}
