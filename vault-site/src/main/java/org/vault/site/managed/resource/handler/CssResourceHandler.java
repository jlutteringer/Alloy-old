package org.vault.site.managed.resource.handler;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.vault.base.utilities.resources.VResources;
import org.vault.base.utilities.url.Urls;
import org.vault.site.resource.handler.AbstractVaultResourceHandler;

@Component
public class CssResourceHandler extends AbstractVaultResourceHandler {
	@Value("${project.version}")
	private String version;

	@Override
	public boolean canHandle(String path) {
		if (path.endsWith(".css")) {
			return true;
		}
		return false;
	}

	@Override
	public Resource getFileContents(String path, List<Resource> locations) {
		String cssPath = Urls.unVersion(path, version);
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
