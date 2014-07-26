package org.vault.site.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.vault.base.domain.phase.Phase;
import org.vault.base.enumeration.VEnumerations;
import org.vault.base.reflection.VReflection;
import org.vault.site.managed.request.RequestLifecycle;

public class VaultFilterProxy extends DelegatingFilterProxy implements VaultFilter {
	private final Logger logger = LogManager.getLogger(this.getClass());
	protected RequestLifecycle requestLifecycle = RequestLifecycle.PRE_SECURITY;
	protected Phase lifecyclePhase = Phase.NORMAL;

	public VaultFilterProxy(Filter filter) {
		super(filter);
	}

	@Override
	protected void invokeDelegate(
			Filter delegate, ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		if (logger.isDebugEnabled()) {
			logger.debug("VaultFilterProxy invoking delegate [" + VReflection.getField(this, "delegate", Filter.class) + "]");
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
		this.requestLifecycle = VEnumerations.getInstance(requestLifecycle, RequestLifecycle.class);
	}

	public void setLifecyclePhase(String phase) {
		this.lifecyclePhase = VEnumerations.getInstance(phase, Phase.class);
	}
}