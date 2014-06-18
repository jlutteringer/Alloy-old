package org.vault.persistence.managed.configuration;

import java.util.List;

import org.springframework.stereotype.Component;
import org.vault.base.facets.FacetedObject;
import org.vault.base.module.domain.ModuleFacet;
import org.vault.base.utilities.configuration.ConfigurationLocation;
import org.vault.core.facets.service.ModuleFacetDelegate;
import org.vault.persistence.facets.PersistenceModuleFacet;

import com.google.common.collect.Lists;

@Component
public class PersistenceFacetDelegate extends ModuleFacetDelegate<PersistenceModuleFacet> {
	@Override
	protected PersistenceModuleFacet getFacet(FacetedObject<ModuleFacet> object) {
		return new ConcretePersistenceModuleFacet();
	}

	static class ConcretePersistenceModuleFacet implements PersistenceModuleFacet {
		private List<ConfigurationLocation> persistenceContextConfigurationLocations = Lists.newArrayList();

		@Override
		public List<ConfigurationLocation> getPersistenceContextConfigurationLocations() {
			return persistenceContextConfigurationLocations;
		}
	}
}