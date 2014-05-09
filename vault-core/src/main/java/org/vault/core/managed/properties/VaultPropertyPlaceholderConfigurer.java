package org.vault.core.managed.properties;

import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.vault.base.resources.stream.ResourceInputStream;
import org.vault.base.utilities.configuration.Configurations;
import org.vault.bootstrap.managed.properties.configuration.PropertiesConfigurationManager;
import org.vault.core.context.VaultApplicationContext;
import org.vault.core.context.VaultApplicationContextAware;

@Component
public class VaultPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer implements VaultApplicationContextAware, InitializingBean {
	private VaultApplicationContext applicationContext;

	@Override
	public void afterPropertiesSet() throws Exception {
		List<ResourceInputStream> configurations =
				Configurations.getConfigurations(
						applicationContext.getInitializationContext().getBean(PropertiesConfigurationManager.class).buildConfigurationLocations(), applicationContext);

		this.setLocations(ResourceInputStream.toResources(configurations).toArray(new Resource[0]));
	}

	@Override
	public void setApplicationContext(VaultApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}