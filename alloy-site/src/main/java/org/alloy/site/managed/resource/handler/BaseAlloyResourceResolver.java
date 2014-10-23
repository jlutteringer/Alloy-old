package org.alloy.site.managed.resource.handler;

import java.io.IOException;
import java.util.List;

import org.alloy.metal.domain.Path;
import org.alloy.metal.url._Url;
import org.alloy.site.resource.handler.AbstractAlloyResourceResolver;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class BaseAlloyResourceResolver extends AbstractAlloyResourceResolver {
	@Override
	public boolean canHandle(Path path) {
		return true;
	}

	@Override
	public Resource getFileContents(Path path, List<Resource> locations) {
		String unversionedPath = _Url.unVersion(path.getPath(), this.getVersion());

		for (Resource location : locations) {
			try {
				logger.trace("Trying relative path [" + unversionedPath + "] against base location: " + location);

				Resource resource = location.createRelative("/resources" + unversionedPath);
				if (resource.exists() && resource.isReadable()) {
					logger.debug("Found matching resource: " + resource);

					return resource;
				}
				else {
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
