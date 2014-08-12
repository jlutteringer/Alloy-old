package org.alloy.site.request;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.alloy.site.filter.AbstractAlloyFilter;
import org.alloy.site.filter.AlloyFilter;
import org.springframework.web.context.request.ServletWebRequest;

public abstract class StandaloneRequestProcessor extends AbstractAlloyFilter implements AlloyFilter, RequestProcessor {
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		this.processRequest(new ServletWebRequest(request, response));
		filterChain.doFilter(request, response);
	}
}