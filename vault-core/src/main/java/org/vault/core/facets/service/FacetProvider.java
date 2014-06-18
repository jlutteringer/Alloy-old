package org.vault.core.facets.service;

import org.vault.base.facets.Facet;
import org.vault.base.facets.FacetedObject;

public interface FacetProvider {
	public <T extends Facet, N extends T> N createFacet(Class<N> clazz, FacetedObject<T> object);
}