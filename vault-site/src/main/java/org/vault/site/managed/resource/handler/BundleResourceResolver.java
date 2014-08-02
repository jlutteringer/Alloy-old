package org.vault.site.managed.resource.handler;

import java.util.List;

import org.alloy.metal.domain.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.vault.site.managed.resource.service.bundle.ResourceBundlingService;
import org.vault.site.resource.handler.AbstractVaultResourceResolver;

@Component
public class BundleResourceResolver extends AbstractVaultResourceResolver {
	@Autowired
	private ResourceBundlingService resourceBundlingService;

	@Override
	public boolean canHandle(Path path) {
		return resourceBundlingService.hasBundleForName(path.getPath());
	}

	@Override
	public Resource getFileContents(Path path, List<Resource> locations) {
		return resourceBundlingService.resolveBundleForName(path.getPath());
	}

	@Override
	public int getOrder() {
		return 1000;
	}
}