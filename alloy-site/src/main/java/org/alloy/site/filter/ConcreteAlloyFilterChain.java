package org.alloy.site.filter;

import java.util.List;

import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;

import org.alloy.metal.object.LoggingObject;
import org.alloy.metal.order.Phase;
import org.alloy.site.request.RequestLifecycle;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.google.common.collect.Lists;

public class ConcreteAlloyFilterChain extends LoggingObject implements AlloyFilterChain {
	protected RequestMatcher requestMatcher;
	protected List<Filter> filters = Lists.newArrayList();
	protected RequestLifecycle requestLifecycle;
	protected Phase lifecyclePhase;

	@Override
	public List<Filter> getFilters() {
		return filters;
	}

	@Override
	public boolean matches(HttpServletRequest request) {
		return requestMatcher.matches(request);
	}

	@Override
	public String toString() {
		return "[ " + requestMatcher + ", " + filters + "]";
	}

	@Override
	public RequestLifecycle getRequestLifecycle() {
		return requestLifecycle;
	}

	@Override
	public Phase getLifecyclePhase() {
		return lifecyclePhase;
	}

	public void setRequestLifecycle(RequestLifecycle requestLifecycle) {
		this.requestLifecycle = requestLifecycle;
	}

	public void setLifecyclePhase(Phase lifecyclePhase) {
		this.lifecyclePhase = lifecyclePhase;
	}

	public void setRequestMatcher(RequestMatcher requestMatcher) {
		this.requestMatcher = requestMatcher;
	}

	public void addFilter(Filter filter) {
		this.filters.add(filter);
	}
}