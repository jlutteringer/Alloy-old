package org.vault.site.managed.resource.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.vault.base.request.Path;
import org.vault.site.managed.resource.service.bundle.ResourceBundlingService;
import org.vault.site.resource.handler.AbstractVaultPathTransformer;

@Component
public class BundlePathTransformer extends AbstractVaultPathTransformer {
	@Autowired
	private ResourceBundlingService bundlingService;

	@Override
	public boolean canHandle(Path path) {
		return bundlingService.hasBundleForResource(path.getPath());
	}

	@Override
	public Path transform(Path path) {
		path.setPath(bundlingService.getBundleNameForResource(path.getPath()));
		return path;
	}
}