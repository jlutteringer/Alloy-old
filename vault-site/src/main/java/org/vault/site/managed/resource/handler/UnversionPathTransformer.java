package org.vault.site.managed.resource.handler;

import org.alloy.metal.domain.Path;
import org.alloy.metal.utilities._Url;
import org.springframework.stereotype.Component;
import org.vault.site.resource.handler.AbstractVaultPathTransformer;

@Component
public class UnversionPathTransformer extends AbstractVaultPathTransformer {
	@Override
	public boolean canHandle(Path path) {
		return _Url.isVersioned(path.getPath(), this.getVersion());
	}

	@Override
	public Path transform(Path path) {
		path.setPath(_Url.unVersion(path.getPath(), this.getVersion()));
		return path;
	}

	@Override
	public int getOrder() {
		return 1000;
	}
}