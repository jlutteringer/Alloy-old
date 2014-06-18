package org.vault.core.facets.domain;

import org.vault.base.facets.Facet;
import org.vault.core.facets.service.FacetProvider;

public abstract class ProvidedFacetedObject<T extends Facet> extends AbstractFacetedObject<T> {
	@Override
	protected <N extends T> N createFacet(Class<N> clazz) {
		return this.getFacetProvider().createFacet(clazz, this);
	}

	protected abstract FacetProvider getFacetProvider();
}
