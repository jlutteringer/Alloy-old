package org.vault.site.boot.managed.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.stereotype.Component;
import org.vault.base.filter.VaultPrimeFilter;

@Component
public class VaultFilterRegistrationBean extends FilterRegistrationBean {
	@Autowired
	public VaultFilterRegistrationBean(VaultPrimeFilter primeFilter) {
		super(primeFilter);
	}
}