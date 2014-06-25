package org.vault.site.resource.handler;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.Resource;
import org.vault.base.spring.beans.VaultBean;

public abstract class AbstractVaultResourceHandler extends VaultBean implements VaultResourceHandler {
	public static final int DEFAULT_ORDER = 10000;

	/**
	* Attempts to retrive the requested resource from cache. If not cached, generates the resource, caches it,
	* and then returns it
	*
	* @param request
	* @param location
	* @return the generated resource
	*/
	@Override
	public Resource getResource(final String path, final List<Resource> locations) {
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
	protected Resource getRawResource(String path, List<Resource> locations) {
		for (Resource location : locations) {
			try {
				Resource resource = location.createRelative(path);
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

	@Override
	public int getOrder() {
		return DEFAULT_ORDER;
	}
}
