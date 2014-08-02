package org.vault.site.managed.resource.handler;

import org.alloy.metal.domain.Path;
import org.alloy.metal.resource._Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.vault.site.managed.resource.less.VaultLessCompiler;
import org.vault.site.resource.handler.AbstractVaultResourceTransformer;

@Component
public class LessToCssResourceTransformer extends AbstractVaultResourceTransformer {
	@Autowired
	private VaultLessCompiler lessCompiler;

	@Override
	public boolean canHandle(Path path, Resource resource) {
		if (resource instanceof UrlResource) {
			if (_Resource.getPath(resource).endsWith(".less")) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Resource transform(Path path, Resource resource) {
		return lessCompiler.compile((UrlResource) resource);
	}
}