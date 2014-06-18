package org.vault.bootstrap.managed.core.facets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.vault.base.facets.Facet;
import org.vault.base.facets.FacetedObject;
import org.vault.core.facets.service.FacetProvider;

@Component
public class CoreFacetConfigurationService implements FacetProvider {
	@Autowired
	private FacetConfigurationDelegator delegator;

	@Override
	public <T extends Facet, N extends T> N createFacet(Class<N> clazz, FacetedObject<T> object) {
		return delegator.getDelegate(clazz).getFacet(clazz, object);
	}
}