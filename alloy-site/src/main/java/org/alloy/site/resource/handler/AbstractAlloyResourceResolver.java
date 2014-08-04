package org.alloy.site.resource.handler;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import org.alloy.metal.domain.Path;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.Resource;

public abstract class AbstractAlloyResourceResolver extends AbstractResourceHandler implements AlloyResourceResolver {
	/**
	* Attempts to retrive the requested resource from cache. If not cached, generates the resource, caches it,
	* and then returns it
	*
	* @param request
	* @param location
	* @return the generated resource
	*/
	@Override
	public Resource getResource(final Path path, final List<Resource> locations) {
		Resource r = getFileContents(path, locations);
		return r;
	}

	/**
	* This method can be used to read in a resource given a path and at least one resource location
	*
	* @param path
	* @param locations
	* @return the resource from the file system, classpath, etc, if it exists
	*/
	protected Resource getRawResource(Path path, List<Resource> locations) {
		for (Resource location : locations) {
			try {
				Resource resource = location.createRelative(path.getPath());
				if (resource.exists() && resource.isReadable()) {
					return resource;
				}
			} catch (IOException ex) {
				logger.debug("Failed to create relative resource - trying next resource location", ex);
			}
		}
		return null;
	}

	/**
	* @param resource
	* @return the UTF-8 String represetation of the contents of the resource
	*/
	protected String getResourceContents(Resource resource) throws IOException {
		StringWriter writer = null;
		try {
			writer = new StringWriter();
			IOUtils.copy(resource.getInputStream(), writer, "UTF-8");
			return writer.toString();
		} finally {
			if (writer != null) {
				writer.flush();
				writer.close();
			}
		}
	}
}
