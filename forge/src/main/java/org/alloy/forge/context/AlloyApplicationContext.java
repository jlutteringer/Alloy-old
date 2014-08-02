package org.alloy.forge.context;

import org.springframework.context.ConfigurableApplicationContext;

public interface AlloyApplicationContext extends ConfigurableApplicationContext {
	public BootstrappedBeanFactory getBootstrappedBeanFactory();

	public void setBootstrappedBeanFactory(BootstrappedBeanFactory bootstrappedBeanFactory);
}
