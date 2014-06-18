package org.vault.core.module.domain.simple;

import org.springframework.beans.factory.annotation.Autowired;
import org.vault.core.facets.service.FacetProvider;

public class ManagedModule extends SimpleModule {
	@Autowired
	public FacetProvider provider;

	@Override
	protected FacetProvider getFacetProvider() {
		return provider;
	}
}
