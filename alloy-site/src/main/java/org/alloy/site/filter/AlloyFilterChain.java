package org.alloy.site.filter;

import java.util.List;

import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;

import org.alloy.site.managed.request.RequestLifecycleOrdeable;

public interface AlloyFilterChain extends RequestLifecycleOrdeable {
	public boolean matches(HttpServletRequest request);

	public List<Filter> getFilters();
}