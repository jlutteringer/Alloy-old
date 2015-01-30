package org.alloy.forge.context;

import org.alloy.metal.collections._Arrays;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;

public class AlloyXmlApplicationContext extends ClassPathXmlApplicationContext implements AlloyApplicationContext {
	private BootstrappedBeanFactory bootstrappedBeanFactory;
	private Resource mergedResource;

	public AlloyXmlApplicationContext(Resource resource) {
		this.mergedResource = resource;
	}

	@Override
	public BootstrappedBeanFactory getBootstrappedBeanFactory() {
		return bootstrappedBeanFactory;
	}

	@Override
	public void setBootstrappedBeanFactory(BootstrappedBeanFactory bootstrappedBeanFactory) {
		this.bootstrappedBeanFactory = bootstrappedBeanFactory;
	}

	@Override
	protected Resource[] getConfigResources() {
		return _Arrays.array(mergedResource);
	}
}