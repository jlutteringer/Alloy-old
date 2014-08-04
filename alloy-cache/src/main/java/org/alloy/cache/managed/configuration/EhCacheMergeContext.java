package org.alloy.cache.managed.configuration;

import java.util.List;

import org.alloy.cache.managed.configuration.EhCacheMergeContext.EhCacheConfigurationManager;
import org.alloy.cache.managed.configuration.EhCacheMergeContext.EhCacheMergeManager;
import org.alloy.forge.configuration.ConfigurationManager;
import org.alloy.forge.merge.AbstractMergeContext;
import org.alloy.forge.merge.AbstractXmlMergeManager;
import org.alloy.forge.merge.MergeManager;
import org.alloy.forge.module.Module;
import org.alloy.forge.module._Module;
import org.alloy.metal.configuration.AlloyConfigurationConstants;
import org.alloy.metal.configuration.ConfigurationLocation;
import org.alloy.metal.configuration._Configuration;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

@Service
public class EhCacheMergeContext extends AbstractMergeContext<EhCacheConfigurationManager, EhCacheMergeManager> {
	@Service
	public static class EhCacheConfigurationManager extends ConfigurationManager {
		@Override
		protected List<ConfigurationLocation> getSpecificConfigurationLocations(Module module) {
			// FUTURE
			return Lists.newArrayList();
		}

		@Override
		protected List<ConfigurationLocation> getDefaultConfigurationLocations(Module module) {
			List<ConfigurationLocation> locations = Lists.newArrayList();

			locations.add(_Configuration.optional(_Module.moduleRelative(
					_Configuration.createClasspathLocation("ehcache.xml"), module)));

			locations.add(_Configuration.optional(_Module.moduleRelative(
					_Configuration.createClasspathLocation(AlloyConfigurationConstants.CONTEXT_RESOURCE_DIRECTORY + "ehcache.xml"), module)));

			return locations;
		}
	}

	@Service
	public static class EhCacheMergeManager extends AbstractXmlMergeManager implements MergeManager {
		@Override
		protected String getMergeDescriptionLocation() {
			return AlloyConfigurationConstants.EH_CACHE_MERGE_DESCRIPTION_LOCATION;
		}
	}
}