package org.vault.core.managed.bootstrap.configuration.logging;

import java.util.List;

import org.springframework.stereotype.Service;
import org.vault.base.utilities.configuration.ConfigurationLocation;
import org.vault.core.managed.bootstrap.configuration.ConfigurationManager;
import org.vault.core.module.domain.Module;

@Service
public class LoggingConfigurationManager extends ConfigurationManager {
	@Override
	protected List<ConfigurationLocation> getSpecificConfigurationLocations(Module module) {
		return module.getLog4jConfigurationLocations();
	}

	@Override
	protected List<ConfigurationLocation> getDefaultConfigurationLocations(Module module) {
		// TODO Auto-generated method stub
		return null;
	}
}
