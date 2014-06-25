package org.vault.site.managed.resource.handler;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.vault.base.utilities.url.Urls;
import org.vault.site.resource.handler.AbstractVaultResourceHandler;

@Component
public class BaseVaultResourceHandler extends AbstractVaultResourceHandler {
	@Value("${project.version}")
	private String version;

	@Override
	public boolean canHandle(String path) {
		return true;
	}

	@Override
	public Resource getFileContents(String path, List<Resource> locations) {
		String unversionedPath = Urls.unVersion(path, version);

		for (Resource location : locations) {
			try {
				if (logger.isDebugEnabled()) {
					logger.debug("Trying relative path [" + unversionedPath + "] against base location: " + location);
				}
				Resource resource = location.createRelative("/resources" + unversionedPath);
				if (resource.exists() && resource.isReadable()) {
					if (logger.isDebugEnabled()) {
						logger.debug("Found matching resource: " + resource);
					}

					return resource;
				}
				else if (logger.isTraceEnabled()) {
					logger.trace("Relative resource doesn't exist or isn't readable: " + resource);
				}
			} catch (IOException ex) {
				logger.debug("Failed to create relative resource - trying next resource location", ex);
			}
		}
		return null;
	}

	@Override
	public int getOrder() {
		return Integer.MAX_VALUE;
	}
}
