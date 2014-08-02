package org.alloy.forge.module;

import org.alloy.metal.facets.FacetProvider;

public class DefaultApplicationModule extends ApplicationModule {
	private FacetProvider provider;

	public DefaultApplicationModule(FacetProvider provider) {
		super();
		this.provider = provider;
	}

	@Override
	protected FacetProvider getFacetProvider() {
		return provider;
	}
}