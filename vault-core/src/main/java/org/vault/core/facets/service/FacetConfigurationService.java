package org.vault.core.facets.service;

import org.vault.base.facets.Facet;
import org.vault.base.facets.FacetedObject;

public interface FacetConfigurationService {
	public <T extends Facet> T getFacet(Class<T> clazz, FacetedObject<?> object);

	public static FacetConfigurationService get() {
		return null;
	}
}