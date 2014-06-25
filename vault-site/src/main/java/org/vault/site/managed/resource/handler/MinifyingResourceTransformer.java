package org.vault.site.managed.resource.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.vault.base.utilities.resources.VResources;
import org.vault.site.managed.resource.service.ResourceMinificationService;
import org.vault.site.resource.handler.AbstractVaultResourceTransformer;

@Component
public class MinifyingResourceTransformer extends AbstractVaultResourceTransformer {
	@Autowired
	private ResourceMinificationService resourceMinificationService;

	@Override
	public boolean canHandle(String path, Resource resource) {
		if (path.endsWith(".css") || path.endsWith(".js")) {
			return true;
		}
		return false;
	}

	@Override
	public Resource transform(String path, Resource resource) {
		byte[] bytes = resourceMinificationService.minify(path, VResources.getBytes(resource));
		return VResources.getResource(bytes);
	}

	@Override
	public int getOrder() {
		return Integer.MAX_VALUE;
	}
}