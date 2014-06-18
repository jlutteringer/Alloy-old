package org.vault.core.module.domain.simple;

import org.vault.core.facets.service.FacetProvider;

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