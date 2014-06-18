package org.vault.core.facets.domain;

import java.util.Map;

import org.vault.base.facets.Facet;
import org.vault.base.facets.FacetedObject;

import com.google.common.collect.Maps;

public abstract class AbstractFacetedObject<T extends Facet> implements FacetedObject<T> {
	protected Map<Class<?>, T> facets = Maps.newHashMap();

	@Override
	public <N extends T> N getFacet(Class<N> clazz) {
		N facet = this.getExistingFacet(clazz);
		if (facet == null) {
			facet = this.createFacet(clazz);
			this.addFacet(clazz, facet);
		}

		return facet;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <N extends T> N getExistingFacet(Class<N> clazz) {
		return (N) facets.get(clazz);
	}

	@Override
	public <N extends T> void addFacet(Class<N> clazz, T facet) {
		facets.put(clazz, facet);
	}

	protected abstract <N extends T> N createFacet(Class<N> clazz);
}