package org.alloy.site.managed.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.alloy.site.managed.request.RequestLifecycle;
import org.alloy.site.managed.request.RequestProcessorContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

public abstract class AlloyRequestProcessorFilter extends OncePerRequestFilter {
	private final Logger logger = LogManager.getLogger(this.getClass());

	@Autowired
	private RequestProcessorContext requestProcessorContext;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		logger.debug("Calling request processor context with lifecycle [" + this.getRequestLifecycle() + "]");
		requestProcessorContext.process(this.getRequestLifecycle(), new ServletWebRequest(request, response));
		filterChain.doFilter(request, response);
	}

	protected abstract RequestLifecycle getRequestLifecycle();
}