package org.vault.site.managed.resource.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.vault.base.request.Path;
import org.vault.base.resource.VResources;
import org.vault.site.managed.resource.service.ResourceMinificationService;
import org.vault.site.resource.handler.AbstractVaultResourceTransformer;

@Component
public class MinifyingResourceTransformer extends AbstractVaultResourceTransformer {
	@Autowired
	private ResourceMinificationService resourceMinificationService;

	@Override
	public boolean canHandle(Path path, Resource resource) {
		if (path.getPath().endsWith(".css") || path.getPath().endsWith(".js")) {
			return true;
		}
		return false;
	}

	@Override
	public Resource transform(Path path, Resource resource) {
		byte[] bytes = resourceMinificationService.minify(path.getPath(), VResources.getBytes(resource));
		return VResources.getResource(bytes);
	}

	@Override
	public int getOrder() {
		return Integer.MAX_VALUE;
	}
}