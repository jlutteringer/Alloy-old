package org.vault.site.managed.resource.handler;

import java.util.List;

import org.alloy.metal.domain.Path;
import org.alloy.metal.resource._Resource;
import org.alloy.metal.utilities._Url;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.vault.site.resource.handler.AbstractVaultResourceResolver;

@Component
public class CssResourceResolver extends AbstractVaultResourceResolver {
	@Override
	public boolean canHandle(Path path) {
		if (path.getPath().endsWith(".css")) {
			return true;
		}
		return false;
	}

	@Override
	public Resource getFileContents(Path path, List<Resource> locations) {
		String cssPath = _Url.unVersion(path.getPath(), this.getVersion());
		String lessPath = cssPath.replace(".css", ".less");

		for (Resource location : locations) {
			Resource relativeResource = _Resource.findExistingRelative(location, "/resources" + lessPath);

			if (relativeResource == null) {
				relativeResource = _Resource.findExistingRelative(location, "/resources" + cssPath);
			}

			if (relativeResource != null) {
				return relativeResource;
			}
		}

		return null;
	}
}
