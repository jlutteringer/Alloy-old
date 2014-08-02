package org.alloy.metal.facets;

public interface FacetedObject<T extends Facet> {
	public <N extends T> N getFacet(Class<N> clazz);

	public <N extends T> N getExistingFacet(Class<N> clazz);

	public <N extends T> void addFacet(Class<N> clazz, T facet);
}