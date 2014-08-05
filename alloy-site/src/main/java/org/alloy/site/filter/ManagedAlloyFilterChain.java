package org.alloy.site.filter;

import org.alloy.metal.order.Phase;
import org.alloy.site.request.RequestLifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.web.util.matcher.RequestMatcher;

public abstract class ManagedAlloyFilterChain extends ConcreteAlloyFilterChain {
	public ManagedAlloyFilterChain() {
		this.requestLifecycle = RequestLifecycle.DEFAULT;
		this.lifecyclePhase = Phase.DEFAULT;
	}

	@Override
	@Autowired
	@Qualifier("defaultRequestMatcher")
	public void setRequestMatcher(RequestMatcher requestMatcher) {
		this.requestMatcher = requestMatcher;
	}
}