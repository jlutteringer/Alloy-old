package org.vault.persistence.managed.configuration;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vault.base.application.ApplicationMetaData;
import org.vault.base.module.domain.Module;
import org.vault.base.utilities.configuration.ConfigurationLocation;
import org.vault.base.utilities.configuration.Configurations;
import org.vault.base.utilities.constants.VConfigurationFileConstants;
import org.vault.bootstrap.managed.configuration.ConfigurationManager;
import org.vault.persistence.facets.PersistenceModuleFacet;

@Service
public class PersistenceConfigurationManager extends ConfigurationManager {
	@Autowired
	private ApplicationMetaData application;

	@Override
	protected List<ConfigurationLocation> getSpecificConfigurationLocations(Module module) {
		return module.getFacet(PersistenceModuleFacet.class).getPersistenceContextConfigurationLocations();
	}

	@Override
	protected List<ConfigurationLocation> getDefaultConfigurationLocations(Module module) {
		return Collections.singletonList(
				Configurations.optional(
						Configurations.moduleRelative(
								Configurations.createEnvironmentLocation(VConfigurationFileConstants.getPersistenceFileStructure(), application.getEnvironment().getType()), module)));
	}

}
