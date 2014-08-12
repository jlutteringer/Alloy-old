package org.alloy.site.managed.filter;

import java.util.List;

import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;

import org.alloy.site.filter.AlloyFilterChain;
import org.alloy.site.request.AbstractRequestLifecycleOrderable;
import org.springframework.stereotype.Component;

@Component
public class NoOpAlloyFilterChain extends AbstractRequestLifecycleOrderable implements AlloyFilterChain {
	@Override
	public boolean matches(HttpServletRequest request) {
		return false;
	}

	@Override
	public List<Filter> getFilters() {
		return null;
	}
}