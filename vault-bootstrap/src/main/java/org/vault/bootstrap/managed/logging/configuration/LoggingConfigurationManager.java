package org.vault.bootstrap.managed.logging.configuration;

import java.util.Collections;
import java.util.List;

import org.alloy.metal.application.ApplicationMetaData;
import org.alloy.metal.configuration.ConfigurationLocation;
import org.alloy.metal.configuration._Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vault.base.module.domain.Module;
import org.vault.base.module.domain.PrimaryModuleFacet;
import org.vault.base.utilities.constants.AlloyConfigurationConstants;
import org.vault.bootstrap.managed.configuration.ConfigurationManager;

@Service
public class LoggingConfigurationManager extends ConfigurationManager {
	@Autowired
	private ApplicationMetaData application;

	@Override
	protected List<ConfigurationLocation> getSpecificConfigurationLocations(Module module) {
		return module.getFacet(PrimaryModuleFacet.class).getLog4jConfigurationLocations();
	}

	@Override
	protected List<ConfigurationLocation> getDefaultConfigurationLocations(Module module) {
		return Collections.singletonList(
				_Configuration.optional(
						_Configuration.moduleRelative(
								_Configuration.createEnvironmentLocation(AlloyConfigurationConstants.getLogFileStructure(), application.getEnvironment().getType()), module)));
	}
}