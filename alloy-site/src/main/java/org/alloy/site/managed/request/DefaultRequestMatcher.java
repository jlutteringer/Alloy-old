package org.alloy.site.managed.request;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

@Component
public class DefaultRequestMatcher implements RequestMatcher {
	@Override
	public boolean matches(HttpServletRequest request) {
		// FUTURE
		return true;
	}
}