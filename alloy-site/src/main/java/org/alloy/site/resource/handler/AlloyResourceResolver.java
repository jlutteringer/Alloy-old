package org.alloy.site.resource.handler;

import java.util.List;

import org.alloy.metal.domain.Path;
import org.alloy.metal.order.Orderable;
import org.springframework.core.io.Resource;

public interface AlloyResourceResolver extends Orderable {
	/**
	* @param path
	* @return booelean determining whether or not this handler is able to handle the given request
	*/
	public boolean canHandle(Path path);

	/**
	* @param path
	* @param locations
	* @return the Resource representing this file
	*/
	public Resource getFileContents(Path path, List<Resource> locations);

	/**
	* Attempts to retrive the requested resource from cache. If not cached, generates the resource, caches it,
	* and then returns it
	*
	* @param request
	* @param location
	* @return the generated resource
	*/
	public Resource getResource(final Path path, final List<Resource> locations);
}
