package org.alloy.forge.managed.configuration;

import java.util.Collections;
import java.util.List;

import org.alloy.forge.configuration.ConfigurationManager;
import org.alloy.forge.module.Module;
import org.alloy.forge.module.PrimaryModuleFacet;
import org.alloy.metal.application.ApplicationMetaData;
import org.alloy.metal.configuration.AlloyConfigurationConstants;
import org.alloy.metal.configuration.ConfigurationLocation;
import org.alloy.metal.configuration._Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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