package org.vault.site.boot;

import org.springframework.boot.context.embedded.XmlEmbeddedWebApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.vault.bootstrap.context.BootstrappedContext;
import org.vault.bootstrap.context.VaultApplicationContext;

public class VaultEmbeddedWebApplicationContext extends XmlEmbeddedWebApplicationContext implements VaultApplicationContext {
	private BootstrappedContext initializationContext;

	public VaultEmbeddedWebApplicationContext(Resource resource) {
		this.load(resource);
	}

	@Override
	public BootstrappedContext getInitializationContext() {
		return initializationContext;
	}

	@Override
	public void setInitializationContext(BootstrappedContext initializationContext) {
		this.initializationContext = initializationContext;
	}

	@Override
	protected ResourcePatternResolver getResourcePatternResolver() {
		return new PathMatchingResourcePatternResolver(this.getClassLoader());
	}
}
