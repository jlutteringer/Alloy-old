package org.alloy.site.boot.managed.filter;

import org.alloy.metal.filter.AlloyPrimeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.stereotype.Component;

@Component
public class AlloyFilterRegistrationBean extends FilterRegistrationBean {
	@Autowired
	public AlloyFilterRegistrationBean(AlloyPrimeFilter primeFilter) {
		super(primeFilter);
	}
}