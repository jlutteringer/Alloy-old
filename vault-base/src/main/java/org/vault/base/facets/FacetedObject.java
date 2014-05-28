package org.vault.base.facets;

public interface FacetedObject<T extends Facet> {
	public <N extends T> N getFacet(Class<N> clazz);
}