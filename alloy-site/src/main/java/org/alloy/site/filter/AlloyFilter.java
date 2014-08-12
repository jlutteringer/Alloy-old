package org.alloy.site.filter;

import javax.servlet.Filter;

import org.alloy.site.request.RequestLifecycleOrdeable;
import org.springframework.security.web.util.matcher.RequestMatcher;

public interface AlloyFilter extends Filter, RequestLifecycleOrdeable {
	public RequestMatcher getRequestMatcher();
}