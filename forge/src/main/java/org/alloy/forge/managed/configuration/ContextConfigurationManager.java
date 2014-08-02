package org.alloy.forge.managed.configuration;

import java.util.List;

import org.alloy.forge.configuration.ConfigurationManager;
import org.alloy.forge.module.Module;
import org.alloy.forge.module.PrimaryModuleFacet;
import org.alloy.metal.configuration.AlloyConfigurationConstants;
import org.alloy.metal.configuration.ConfigurationLocation;
import org.alloy.metal.configuration._Configuration;
import org.springframework.stereotype.Service;

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

		locations.add(_Configuration.optional(_Configuration.moduleRelative(
				_Configuration.createClasspathLocation("*-applicationContext.xml"), module)));

		locations.add(_Configuration.optional(_Configuration.moduleRelative(
				_Configuration.createClasspathLocation(AlloyConfigurationConstants.CONTEXT_RESOURCE_DIRECTORY + "*-applicationContext.xml"), module)));

		return locations;
	}
}
