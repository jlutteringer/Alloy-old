package org.vault.site.managed.resource.handler;

import org.alloy.metal.domain.Path;
import org.alloy.metal.resource._Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.vault.site.managed.resource.service.ResourceMinificationService;
import org.vault.site.resource.handler.AbstractVaultResourceTransformer;

@Component
public class MinifyingResourceTransformer extends AbstractVaultResourceTransformer {
	@Autowired
	private ResourceMinificationService resourceMinificationService;

	@Override
	public boolean canHandle(Path path, Resource resource) {
		if (!resourceMinificationService.getEnabled()) {
			return false;
		}

		if (path.getPath().endsWith(".css") || path.getPath().endsWith(".js")) {
			return true;
		}
		return false;
	}

	@Override
	public Resource transform(Path path, Resource resource) {
		byte[] bytes = resourceMinificationService.minify(path.getPath(), _Resource.getBytes(resource));
		return _Resource.getResource(bytes);
	}

	@Override
	public int getOrder() {
		return Integer.MAX_VALUE;
	}
}