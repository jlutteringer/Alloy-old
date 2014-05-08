package org.vault.extensibility.context;

import org.springframework.context.support.AbstractXmlApplicationContext;
import org.springframework.core.io.Resource;

public class ResourceApplicationContext extends AbstractXmlApplicationContext {
	protected Resource resource;

	public ResourceApplicationContext(Resource resource) {
		this.resource = resource;
	}

	@Override
	protected Resource[] getConfigResources() {
		return new Resource[] { resource };
	}
}
