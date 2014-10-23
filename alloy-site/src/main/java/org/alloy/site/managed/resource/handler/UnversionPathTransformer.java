package org.alloy.site.managed.resource.handler;

import org.alloy.metal.domain.Path;
import org.alloy.metal.url._Url;
import org.alloy.site.resource.handler.AbstractAlloyPathTransformer;
import org.springframework.stereotype.Component;

@Component
public class UnversionPathTransformer extends AbstractAlloyPathTransformer {
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