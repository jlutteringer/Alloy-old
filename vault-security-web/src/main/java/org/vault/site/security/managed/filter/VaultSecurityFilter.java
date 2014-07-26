package org.vault.site.security.managed.filter;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.vault.site.filter.VaultFilterProxy;
import org.vault.site.managed.request.RequestLifecycle;

@Component
public class VaultSecurityFilter extends VaultFilterProxy {
	@Autowired
	public VaultSecurityFilter(@Qualifier("org.springframework.security.filterChainProxy") Filter filter) {
		super(filter);
		requestLifecycle = RequestLifecycle.SECURITY;
	}
}