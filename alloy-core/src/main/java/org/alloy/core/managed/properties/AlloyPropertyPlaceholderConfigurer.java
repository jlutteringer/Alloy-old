package org.alloy.core.managed.properties;

import java.util.List;

import org.alloy.forge.context.AlloyApplicationContext;
import org.alloy.forge.context.AlloyApplicationContextAware;
import org.alloy.forge.managed.configuration.PropertiesConfigurationManager;
import org.alloy.metal.configuration._Configuration;
import org.alloy.metal.resource.ResourceInputStream;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class AlloyPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer implements AlloyApplicationContextAware, InitializingBean {
	private AlloyApplicationContext applicationContext;

	@Override
	public void afterPropertiesSet() throws Exception {
		List<ResourceInputStream> configurations =
				_Configuration.getConfigurations(
						applicationContext.getBootstrappedBeanFactory().getBean(PropertiesConfigurationManager.class).buildConfigurationLocations(), applicationContext);

		this.setLocations(ResourceInputStream.toResources(configurations).toArray(new Resource[0]));
	}

	@Override
	public void setApplicationContext(AlloyApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}