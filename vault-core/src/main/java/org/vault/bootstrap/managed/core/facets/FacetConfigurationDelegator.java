package org.vault.bootstrap.managed.core.facets;

import org.alloy.metal.spring.delegator.AbstractDelegator;
import org.springframework.stereotype.Component;
import org.vault.base.facets.Facet;
import org.vault.core.facets.service.FacetDelegate;

@Component
public class FacetConfigurationDelegator extends AbstractDelegator<FacetDelegate<?>, Class<? extends Facet>> {

}
