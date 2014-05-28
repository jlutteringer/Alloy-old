package org.vault.core.facets.domain;

import org.vault.base.facets.Facet;
import org.vault.base.facets.FacetedObject;
import org.vault.core.facets.service.FacetConfigurationService;

public abstract class AbstractFacetedObject<T extends Facet> implements FacetedObject<T> {
	@Override
	public <N extends T> N getFacet(Class<N> clazz) {
		return FacetConfigurationService.get().getFacet(clazz, this);
	}
}