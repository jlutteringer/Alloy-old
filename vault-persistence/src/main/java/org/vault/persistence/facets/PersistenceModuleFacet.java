package org.vault.persistence.facets;

import java.util.List;

import org.vault.base.module.domain.ModuleFacet;
import org.vault.base.utilities.configuration.ConfigurationLocation;

public interface PersistenceModuleFacet extends ModuleFacet {
	public List<ConfigurationLocation> getPersistenceContextConfigurationLocations();
}
