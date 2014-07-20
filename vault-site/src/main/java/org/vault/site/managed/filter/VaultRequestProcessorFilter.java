package org.vault.site.managed.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;
import org.vault.site.managed.request.RequestLifecycle;
import org.vault.site.managed.request.RequestProcessorContext;

public abstract class VaultRequestProcessorFilter extends OncePerRequestFilter {
	@Autowired
	private RequestProcessorContext requestProcessorContext;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		requestProcessorContext.process(this.getRequestLifecycle(), new ServletWebRequest(request, response));
		filterChain.doFilter(request, response);
	}

	protected abstract RequestLifecycle getRequestLifecycle();
}