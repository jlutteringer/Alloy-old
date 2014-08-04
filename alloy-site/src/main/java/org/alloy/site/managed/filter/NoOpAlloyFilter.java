package org.alloy.site.managed.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.alloy.site.filter.AlloyFilter;

public class NoOpAlloyFilter implements AlloyFilter {
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// No op
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// Passthrough
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		// No op
	}
}