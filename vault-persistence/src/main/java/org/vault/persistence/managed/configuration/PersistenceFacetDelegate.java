package org.vault.persistence.managed.configuration;

import org.springframework.stereotype.Component;

@Component
public class PersistenceFacetDelegate { // extends ModuleFacetDelegate<PersistenceModuleFacet> {
//	@Override
//	protected PersistenceModuleFacet getFacet(FacetedObject<ModuleFacet> object) {
//		return new ConcretePersistenceModuleFacet();
//	}
//
//	static class ConcretePersistenceModuleFacet implements PersistenceModuleFacet {
//		private List<ConfigurationLocation> persistenceContextConfigurationLocations = Lists.newArrayList();
//
//		@Override
//		public List<ConfigurationLocation> getPersistenceContextConfigurationLocations() {
//			return persistenceContextConfigurationLocations;
//		}
//	}
}