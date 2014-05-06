package org.vault.bootstrap.managed.configuration.logging;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;
import org.vault.base.module.domain.Module;
import org.vault.base.utilities.configuration.ConfigurationLocation;
import org.vault.base.utilities.configuration.Configurations;
import org.vault.bootstrap.managed.configuration.ConfigurationManager;
import org.vault.bootstrap.managed.configuration.DirectoryStructure;

@Service
public class LoggingConfigurationManager extends ConfigurationManager {
	@Override
	protected List<ConfigurationLocation> getSpecificConfigurationLocations(Module module) {
		return module.getLog4jConfigurationLocations();
	}

	@Override
	protected List<ConfigurationLocation> getDefaultConfigurationLocations(Module module) {
		return Collections.singletonList(
				Configurations.moduleRelative(
						Configurations.createEnvironmentLocation(DirectoryStructure.getLogFileStructure(), null), module));
	}
}