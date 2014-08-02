package org.alloy.forge.managed.facets;

import org.alloy.metal.facets.Facet;
import org.alloy.metal.facets.FacetProvider;
import org.alloy.metal.facets.FacetedObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CoreFacetConfigurationService implements FacetProvider {
	@Autowired
	private FacetConfigurationDelegator delegator;

	@Override
	public <T extends Facet, N extends T> N createFacet(Class<N> clazz, FacetedObject<T> object) {
		return delegator.getDelegate(clazz).getFacet(clazz, object);
	}
}