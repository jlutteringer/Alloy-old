package org.vault.bootstrap.managed.context.configuration;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;
import org.vault.base.module.domain.Module;
import org.vault.base.utilities.configuration.ConfigurationLocation;
import org.vault.base.utilities.configuration.Configurations;
import org.vault.base.utilities.constants.VConfigurationFileConstants;
import org.vault.bootstrap.managed.configuration.ConfigurationManager;

@Service
public class ContextConfigurationManager extends ConfigurationManager {
	@Override
	protected List<ConfigurationLocation> getSpecificConfigurationLocations(Module module) {
		return module.getModuleConfigurationLocations();
	}

	@Override
	protected List<ConfigurationLocation> getDefaultConfigurationLocations(Module module) {
		return Collections.singletonList(
				Configurations.optional(Configurations.moduleRelative(
						Configurations.createClasspathLocation(VConfigurationFileConstants.CONTEXT_RESOURCE_DIRECTORY + "/*"), module)));
	}
}
