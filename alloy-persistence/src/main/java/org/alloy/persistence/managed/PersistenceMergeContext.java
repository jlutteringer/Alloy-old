package org.alloy.persistence.managed;

import java.util.List;

import org.alloy.forge.configuration.ConfigurationManager;
import org.alloy.forge.merge.AbstractMergeContext;
import org.alloy.forge.merge.AbstractXmlMergeManager;
import org.alloy.forge.merge.MergeManager;
import org.alloy.forge.module.Module;
import org.alloy.forge.module._Module;
import org.alloy.metal.application.ApplicationMetaData;
import org.alloy.metal.configuration.AlloyConfigurationConstants;
import org.alloy.metal.configuration.ConfigurationLocation;
import org.alloy.metal.configuration._Configuration;
import org.alloy.persistence.managed.PersistenceMergeContext.PersistenceConfigurationManager;
import org.alloy.persistence.managed.PersistenceMergeContext.PersistenceMergeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

@Service
public class PersistenceMergeContext extends AbstractMergeContext<PersistenceConfigurationManager, PersistenceMergeManager> {
	@Service
	public static class PersistenceConfigurationManager extends ConfigurationManager {
		@Autowired
		private ApplicationMetaData application;

		@Override
		protected List<ConfigurationLocation> getSpecificConfigurationLocations(Module module) {
			// FUTURE
			return Lists.newArrayList();
		}

		@Override
		protected List<ConfigurationLocation> getDefaultConfigurationLocations(Module module) {
			List<ConfigurationLocation> locations = Lists.newArrayList();

			locations.add(
					_Configuration.optional(
							_Module.moduleRelative(
									_Configuration.createEnvironmentLocation("*" + AlloyConfigurationConstants.PERSISTENCE_FILE_STRUCTURE, application.getEnvironment().getType()), module)));

			return locations;
		}
	}

	@Service
	public static class PersistenceMergeManager extends AbstractXmlMergeManager implements MergeManager {
		@Override
		protected String getMergeDescriptionLocation() {
			return AlloyConfigurationConstants.PERSISTENCE_MERGE_DESCRIPTION_LOCATION;
		}
	}
}