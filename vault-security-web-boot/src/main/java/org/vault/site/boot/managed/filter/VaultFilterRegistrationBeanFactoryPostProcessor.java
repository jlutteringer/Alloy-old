package org.vault.site.boot.managed.filter;

import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.stereotype.Component;
import org.vault.base.filter.VaultRegisterableFilter;

@Component
public class VaultFilterRegistrationBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
	@Autowired
	private List<VaultRegisterableFilter> registerableFilters;

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		for (VaultRegisterableFilter registerableFilter : registerableFilters) {
			FilterRegistrationBean filterRegistrationBean = beanFactory.createBean(FilterRegistrationBean.class);
			filterRegistrationBean.setFilter(registerableFilter);
		}
	}
}