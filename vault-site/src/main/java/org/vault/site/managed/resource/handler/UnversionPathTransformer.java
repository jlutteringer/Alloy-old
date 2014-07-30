package org.vault.site.managed.resource.handler;

import org.springframework.stereotype.Component;
import org.vault.base.request.Path;
import org.vault.base.url.VUrls;
import org.vault.site.resource.handler.AbstractVaultPathTransformer;

@Component
public class UnversionPathTransformer extends AbstractVaultPathTransformer {
	@Override
	public boolean canHandle(Path path) {
		return VUrls.isVersioned(path.getPath(), this.getVersion());
	}

	@Override
	public Path transform(Path path) {
		path.setPath(VUrls.unVersion(path.getPath(), this.getVersion()));
		return path;
	}

	@Override
	public int getOrder() {
		return 1000;
	}
}