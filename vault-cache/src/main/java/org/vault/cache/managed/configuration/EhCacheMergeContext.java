package org.vault.cache.managed.configuration;

import java.util.List;

import org.springframework.stereotype.Service;
import org.vault.base.module.domain.Module;
import org.vault.base.utilities.configuration.ConfigurationLocation;
import org.vault.base.utilities.configuration.Configurations;
import org.vault.base.utilities.constants.VConfigurationFileConstants;
import org.vault.bootstrap.managed.configuration.AbstractMergeContext;
import org.vault.bootstrap.managed.configuration.ConfigurationManager;
import org.vault.bootstrap.managed.merge.AbstractXmlMergeManager;
import org.vault.bootstrap.managed.merge.MergeManager;
import org.vault.cache.managed.configuration.EhCacheMergeContext.EhCacheConfigurationManager;
import org.vault.cache.managed.configuration.EhCacheMergeContext.EhCacheMergeManager;

import com.google.common.collect.Lists;

@Service
public class EhCacheMergeContext extends AbstractMergeContext<EhCacheConfigurationManager, EhCacheMergeManager> {
	@Service
	public static class EhCacheConfigurationManager extends ConfigurationManager {
		@Override
		protected List<ConfigurationLocation> getSpecificConfigurationLocations(Module module) {
			// TODO Auto-generated method stub
			return Lists.newArrayList();
		}

		@Override
		protected List<ConfigurationLocation> getDefaultConfigurationLocations(Module module) {
			List<ConfigurationLocation> locations = Lists.newArrayList();

			locations.add(Configurations.optional(Configurations.moduleRelative(
					Configurations.createClasspathLocation("ehcache.xml"), module)));

			locations.add(Configurations.optional(Configurations.moduleRelative(
					Configurations.createClasspathLocation(VConfigurationFileConstants.CONTEXT_RESOURCE_DIRECTORY + "ehcache.xml"), module)));

			return locations;
		}
	}

	@Service
	public static class EhCacheMergeManager extends AbstractXmlMergeManager implements MergeManager {
		@Override
		protected String getMergeDescriptionLocation() {
			return VConfigurationFileConstants.EH_CACHE_MERGE_DESCRIPTION_LOCATION;
		}
	}
}