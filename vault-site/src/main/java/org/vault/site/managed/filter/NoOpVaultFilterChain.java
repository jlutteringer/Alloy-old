package org.vault.site.managed.filter;

import java.util.List;

import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;
import org.vault.site.filter.VaultFilterChain;

@Component
public class NoOpVaultFilterChain implements VaultFilterChain {
	@Override
	public boolean matches(HttpServletRequest request) {
		return false;
	}

	@Override
	public List<Filter> getFilters() {
		return null;
	}
}