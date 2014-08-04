package org.alloy.site.security.managed.filter;

import javax.servlet.Filter;

import org.alloy.site.filter.AlloyFilterProxy;
import org.alloy.site.managed.request.RequestLifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class AlloySecurityFilter extends AlloyFilterProxy {
	@Autowired
	public AlloySecurityFilter(@Qualifier("org.springframework.security.filterChainProxy") Filter filter) {
		super(filter);
		requestLifecycle = RequestLifecycle.SECURITY;
	}
}