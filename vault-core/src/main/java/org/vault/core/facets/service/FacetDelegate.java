package org.vault.core.facets.service;

import org.vault.base.delegator.Delegate;
import org.vault.base.facets.Facet;
import org.vault.base.facets.FacetedObject;

public interface FacetDelegate<T extends Facet> extends Delegate<Class<? extends Facet>> {
	public <A extends Facet> A getFacet(Class<A> clazz, FacetedObject<?> object);
}