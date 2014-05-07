package org.vault.bootstrap.managed.configuration.logging;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vault.base.application.ApplicationMetadata;
import org.vault.base.module.domain.Module;
import org.vault.base.utilities.configuration.ConfigurationLocation;
import org.vault.base.utilities.configuration.Configurations;
import org.vault.base.utilities.constants.VConfigurationFileConstants;
import org.vault.bootstrap.managed.configuration.ConfigurationManager;

@Service
public class LoggingConfigurationManager extends ConfigurationManager {
	@Autowired
	private ApplicationMetadata application;

	@Override
	protected List<ConfigurationLocation> getSpecificConfigurationLocations(Module module) {
		return module.getLog4jConfigurationLocations();
	}

	@Override
	protected List<ConfigurationLocation> getDefaultConfigurationLocations(Module module) {
		return Collections.singletonList(
				Configurations.moduleRelative(
						Configurations.createEnvironmentLocation(VConfigurationFileConstants.getLogFileStructure(), application.getEnvironment().getType()), module));
	}
}