package org.vault.site.managed.resource.handler;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.vault.base.request.Path;
import org.vault.base.resource.VResources;
import org.vault.base.url.VUrls;
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
		String cssPath = VUrls.unVersion(path.getPath(), this.getVersion());
		String lessPath = cssPath.replace(".css", ".less");

		for (Resource location : locations) {
			Resource relativeResource = VResources.findExistingRelative(location, "/resources" + lessPath);

			if (relativeResource == null) {
				relativeResource = VResources.findExistingRelative(location, "/resources" + cssPath);
			}

			if (relativeResource != null) {
				return relativeResource;
			}
		}

		return null;
	}
}
