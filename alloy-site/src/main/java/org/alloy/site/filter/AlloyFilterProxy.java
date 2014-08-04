package org.alloy.site.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.alloy.metal.enumeration._ExtendableEnumeration;
import org.alloy.metal.order.Phase;
import org.alloy.metal.reflection._Reflection;
import org.alloy.site.managed.request.RequestLifecycle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.filter.DelegatingFilterProxy;

public class AlloyFilterProxy extends DelegatingFilterProxy implements AlloyFilter {
	private final Logger logger = LogManager.getLogger(this.getClass());
	protected RequestLifecycle requestLifecycle = RequestLifecycle.PRE_SECURITY;
	protected Phase lifecyclePhase = Phase.NORMAL;

	public AlloyFilterProxy(Filter filter) {
		super(filter);
	}

	@Override
	protected void invokeDelegate(
			Filter delegate, ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		if (logger.isDebugEnabled()) {
			logger.debug("AlloyFilterProxy invoking delegate [" + _Reflection.getField(this, "delegate", Filter.class) + "]");
		}

		delegate.doFilter(request, response, filterChain);
	}

	@Override
	public RequestLifecycle getRequestLifecycle() {
		return requestLifecycle;
	}

	@Override
	public Phase getLifecyclePhase() {
		return lifecyclePhase;
	}

	public void setRequestLifecycle(String requestLifecycle) {
		this.requestLifecycle = _ExtendableEnumeration.getInstance(requestLifecycle, RequestLifecycle.class);
	}

	public void setLifecyclePhase(String phase) {
		this.lifecyclePhase = _ExtendableEnumeration.getInstance(phase, Phase.class);
	}
}