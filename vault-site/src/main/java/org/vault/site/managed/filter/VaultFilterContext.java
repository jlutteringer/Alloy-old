package org.vault.site.managed.filter;

import java.util.Collections;
import java.util.List;

import org.alloy.metal.order.Orderable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.vault.base.filter.VaultPrimeFilter;
import org.vault.site.filter.ConcreteVaultFilterChain;
import org.vault.site.filter.VaultFilter;
import org.vault.site.filter.VaultFilterChain;
import org.vault.site.filter.VaultFilterChainProxy;
import org.vault.site.managed.request.DefaultRequestMatcher;

import com.google.common.collect.Lists;

@Component
public class VaultFilterContext extends VaultFilterChainProxy implements VaultPrimeFilter {
	@Autowired
	private void initialize(List<VaultFilter> vaultFilters, DefaultRequestMatcher defaultRequestMatcher, List<VaultFilterChain> vaultFilterChains) {
		logger.debug("Initializing vault filter context...");
		List<VaultFilterChain> filterChains = Lists.newArrayList(vaultFilterChains);
		for (VaultFilter filter : vaultFilters) {
			logger.debug("Wrapping filter [" + filter + "] in default filter chain");
			ConcreteVaultFilterChain filterChain = new ConcreteVaultFilterChain();
			filterChain.addFilter(filter);
			filterChain.setRequestMatcher(defaultRequestMatcher);
			filterChain.setLifecyclePhase(filter.getLifecyclePhase());
			filterChain.setRequestLifecycle(filter.getRequestLifecycle());
			filterChains.add(filterChain);
		}

		Collections.sort(filterChains, Orderable.comparator());
		logger.debug("Registering filter chain: " + filterChains);
		this.filterChains = filterChains;
	}
}