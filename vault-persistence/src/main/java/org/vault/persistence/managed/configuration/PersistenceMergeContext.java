package org.vault.persistence.managed.configuration;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vault.base.application.ApplicationMetaData;
import org.vault.base.module.domain.Module;
import org.vault.base.utilities.configuration.ConfigurationLocation;
import org.vault.base.utilities.configuration.Configurations;
import org.vault.base.utilities.constants.VConfigurationFileConstants;
import org.vault.bootstrap.managed.configuration.AbstractMergeContext;
import org.vault.bootstrap.managed.configuration.ConfigurationManager;
import org.vault.bootstrap.managed.merge.AbstractXmlMergeManager;
import org.vault.bootstrap.managed.merge.MergeManager;
import org.vault.persistence.managed.configuration.PersistenceMergeContext.PersistenceConfigurationManager;
import org.vault.persistence.managed.configuration.PersistenceMergeContext.PersistenceMergeManager;

import com.google.common.collect.Lists;

@Service
public class PersistenceMergeContext extends AbstractMergeContext<PersistenceConfigurationManager, PersistenceMergeManager> {
	@Service
	public static class PersistenceConfigurationManager extends ConfigurationManager {
		@Autowired
		private ApplicationMetaData application;

		@Override
		protected List<ConfigurationLocation> getSpecificConfigurationLocations(Module module) {
			// TODO
			return Lists.newArrayList();
		}

		@Override
		protected List<ConfigurationLocation> getDefaultConfigurationLocations(Module module) {
			List<ConfigurationLocation> locations = Lists.newArrayList();

			locations.add(
					Configurations.optional(
							Configurations.moduleRelative(
									Configurations.createEnvironmentLocation("*" + VConfigurationFileConstants.PERSISTENCE_FILE_STRUCTURE, application.getEnvironment().getType()), module)));

			return locations;
		}
	}

	@Service
	public static class PersistenceMergeManager extends AbstractXmlMergeManager implements MergeManager {
		@Override
		protected String getMergeDescriptionLocation() {
			return VConfigurationFileConstants.PERSISTENCE_MERGE_DESCRIPTION_LOCATION;
		}
	}
}