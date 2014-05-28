package org.vault.bootstrap.managed.core.facets;

import org.springframework.stereotype.Component;
import org.vault.base.delegator.AbstractDelegator;
import org.vault.base.facets.Facet;
import org.vault.base.utilities.function.VMethod;
import org.vault.core.facets.service.FacetDelegate;

@Component
public class FacetConfigurationDelegator extends AbstractDelegator<FacetDelegate, Class<?>> {
	public static <T extends Facet> VMethod<T> getFacet(Class<T> clazz, Object... arguments) {
		return VMethod.create("getFacet", clazz, arguments);
	}

	public static VMethod<String> foo(String input) {
		return VMethod.create("foo", String.class, input);
	}
}
