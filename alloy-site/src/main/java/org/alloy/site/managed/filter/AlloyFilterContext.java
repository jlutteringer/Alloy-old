package org.alloy.site.managed.filter;

import java.util.Collections;
import java.util.List;

import org.alloy.metal.filter.AlloyPrimeFilter;
import org.alloy.metal.order.Orderable;
import org.alloy.site.filter.AlloyFilter;
import org.alloy.site.filter.AlloyFilterChain;
import org.alloy.site.filter.AlloyFilterChainProxy;
import org.alloy.site.filter.ConcreteAlloyFilterChain;
import org.alloy.site.managed.request.DefaultRequestMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

@Component
public class AlloyFilterContext extends AlloyFilterChainProxy implements AlloyPrimeFilter {
	@Autowired
	private void initialize(List<AlloyFilter> alloyFilters, DefaultRequestMatcher defaultRequestMatcher, List<AlloyFilterChain> alloyFilterChains) {
		logger.debug("Initializing alloy filter context...");
		List<AlloyFilterChain> filterChains = Lists.newArrayList(alloyFilterChains);
		for (AlloyFilter filter : alloyFilters) {
			logger.debug("Wrapping filter [" + filter + "] in default filter chain");
			ConcreteAlloyFilterChain filterChain = new ConcreteAlloyFilterChain();
			filterChain.addFilter(filter);

			if (filter.getRequestMatcher() != null) {
				filterChain.setRequestMatcher(filter.getRequestMatcher());
			}
			else {
				filterChain.setRequestMatcher(defaultRequestMatcher);
			}

			filterChain.setLifecyclePhase(filter.getLifecyclePhase());
			filterChain.setRequestLifecycle(filter.getRequestLifecycle());
			filterChains.add(filterChain);
		}

		Collections.sort(filterChains, Orderable.comparator());
		logger.debug("Registering filter chain: " + filterChains);
		this.filterChains = filterChains;
	}
}