package org.alloy.site.filter;

import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

public abstract class AbstractAlloyFilter extends OncePerRequestFilter implements AlloyFilter {
	@Override
	public RequestMatcher getRequestMatcher() {
		return null;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}
}