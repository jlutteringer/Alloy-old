package org.alloy.forge.module;

import org.alloy.metal.facets.FacetProvider;
import org.springframework.beans.factory.annotation.Autowired;

public class ManagedModule extends SimpleModule {
	@Autowired
	public FacetProvider provider;

	@Override
	protected FacetProvider getFacetProvider() {
		return provider;
	}
}
