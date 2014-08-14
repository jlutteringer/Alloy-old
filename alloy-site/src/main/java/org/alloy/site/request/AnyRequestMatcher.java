package org.alloy.site.request;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.util.matcher.RequestMatcher;

public class AnyRequestMatcher implements RequestMatcher {
	@Override
	public boolean matches(HttpServletRequest arg0) {
		return true;
	}
}