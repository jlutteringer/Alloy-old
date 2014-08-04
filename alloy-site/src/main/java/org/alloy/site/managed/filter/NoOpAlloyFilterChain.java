package org.alloy.site.managed.filter;

import java.util.List;

import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;

import org.alloy.site.filter.AlloyFilterChain;
import org.springframework.stereotype.Component;

@Component
public class NoOpAlloyFilterChain implements AlloyFilterChain {
	@Override
	public boolean matches(HttpServletRequest request) {
		return false;
	}

	@Override
	public List<Filter> getFilters() {
		return null;
	}
}