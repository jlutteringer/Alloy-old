package org.alloy.metal.facets;


public interface FacetProvider {
	public <T extends Facet, N extends T> N createFacet(Class<N> clazz, FacetedObject<T> object);
}