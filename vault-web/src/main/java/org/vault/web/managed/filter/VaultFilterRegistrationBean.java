package org.vault.web.managed.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.stereotype.Component;
import org.vault.web.security.SecurityConstants;

@Component
public class VaultFilterRegistrationBean extends FilterRegistrationBean {
	@Autowired
	@Qualifier(SecurityConstants.SECURITY_FILTER_BEAN_NAME)
	public void setSecurityFilter(FilterChainProxy filter) {
		super.setFilter(filter);
	}
}
