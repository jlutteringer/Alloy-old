package org.vault.site.filter;

import java.util.List;

import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;

import org.vault.site.managed.request.RequestLifecycleOrdeable;

public interface VaultFilterChain extends RequestLifecycleOrdeable {
	public boolean matches(HttpServletRequest request);

	public List<Filter> getFilters();
}