package org.alloy.site.filter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.FirewalledRequest;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.web.filter.GenericFilterBean;

import com.google.common.collect.Lists;

public class AlloyFilterChainProxy extends GenericFilterBean {
	protected final Logger logger = LogManager.getLogger(this.getClass());

	protected final static String FILTER_APPLIED = FilterChainProxy.class.getName().concat(".APPLIED");
	protected List<AlloyFilterChain> filterChains = Lists.newArrayList();
	protected FilterChainValidator filterChainValidator = new NullFilterChainValidator();
	protected HttpFirewall firewall = new DefaultHttpFirewall();

	@Override
	public void afterPropertiesSet() {
		filterChainValidator.validate(this);
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		boolean clearContext = request.getAttribute(FILTER_APPLIED) == null;
		if (clearContext) {
			try {
				request.setAttribute(FILTER_APPLIED, Boolean.TRUE);
				doFilterInternal(request, response, chain);
			} finally {
				SecurityContextHolder.clearContext();
				request.removeAttribute(FILTER_APPLIED);
			}
		} else {
			doFilterInternal(request, response, chain);
		}
	}

	private void doFilterInternal(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		FirewalledRequest fwRequest = firewall.getFirewalledRequest((HttpServletRequest) request);
		HttpServletResponse fwResponse = firewall.getFirewalledResponse((HttpServletResponse) response);

		List<Filter> filters = getFilters(fwRequest);

		if (filters.size() == 0) {
			if (logger.isDebugEnabled()) {
				logger.debug(UrlUtils.buildRequestUrl(fwRequest) +
						(filters == null ? " has no matching filters" : " has an empty filter list"));
			}

			fwRequest.reset();
			chain.doFilter(fwRequest, fwResponse);
			return;
		}

		VirtualFilterChain vfc = new VirtualFilterChain(fwRequest, chain, filters);
		vfc.doFilter(fwRequest, fwResponse);
	}

	private List<Filter> getFilters(HttpServletRequest request) {
		List<Filter> matchingFilters = Lists.newArrayList();
		for (AlloyFilterChain chain : filterChains) {
			if (chain.matches(request)) {
				matchingFilters.addAll(chain.getFilters());
			}
		}

		return matchingFilters;
	}

	public List<Filter> getFilters(String url) {
		return getFilters(firewall.getFirewalledRequest((new FilterInvocation(url, null).getRequest())));
	}

	public List<AlloyFilterChain> getFilterChains() {
		return Collections.unmodifiableList(filterChains);
	}

	public void setFilterChainValidator(FilterChainValidator filterChainValidator) {
		this.filterChainValidator = filterChainValidator;
	}

	public void setFirewall(HttpFirewall firewall) {
		this.firewall = firewall;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("FilterChainProxy[");
		sb.append("Filter Chains: ");
		sb.append(filterChains);
		sb.append("]");

		return sb.toString();
	}

	private static class VirtualFilterChain implements FilterChain {
		private final Logger logger = LogManager.getLogger(this.getClass());
		private final FilterChain originalChain;
		private final List<Filter> additionalFilters;
		private final FirewalledRequest firewalledRequest;
		private final int size;
		private int currentPosition = 0;

		private VirtualFilterChain(FirewalledRequest firewalledRequest, FilterChain chain, List<Filter> additionalFilters) {
			this.originalChain = chain;
			this.additionalFilters = additionalFilters;
			this.size = additionalFilters.size();
			this.firewalledRequest = firewalledRequest;
		}

		@Override
		public void doFilter(ServletRequest request, ServletResponse response) throws IOException, ServletException {
			if (currentPosition == size) {
				logger.trace(UrlUtils.buildRequestUrl(firewalledRequest)
						+ " reached end of additional filter chain; proceeding with original chain");

				// Deactivate path stripping as we exit the security filter chain
				this.firewalledRequest.reset();

				originalChain.doFilter(request, response);
			} else {
				currentPosition++;

				Filter nextFilter = additionalFilters.get(currentPosition - 1);

				logger.debug(UrlUtils.buildRequestUrl(firewalledRequest) + " at position " + currentPosition + " of "
						+ size + " in additional filter chain; firing Filter: '"
						+ nextFilter.toString() + "'");

				nextFilter.doFilter(request, response, this);
			}
		}
	}

	public interface FilterChainValidator {
		void validate(AlloyFilterChainProxy filterChainProxy);
	}

	private class NullFilterChainValidator implements FilterChainValidator {
		@Override
		public void validate(AlloyFilterChainProxy filterChainProxy) {
			// No op
		}
	}
}