package org.alloy.site.resource.handler;

import org.alloy.metal.domain.Path;
import org.alloy.metal.order.Orderable;
import org.springframework.core.io.Resource;

public interface AlloyResourceTransformer extends Orderable {
	/**
	* @param path
	* @return booelean determining whether or not this handler is able to handle the given request
	*/
	public boolean canHandle(Path path, Resource resource);

	/**
	* Attempts to retrive the requested resource from cache. If not cached, generates the resource, caches it,
	* and then returns it
	*
	* @param request
	* @param location
	* @return the generated resource
	*/
	public Resource transform(Path path, Resource resource);
}