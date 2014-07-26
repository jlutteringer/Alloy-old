package org.vault.site.resource.handler;

import org.springframework.core.io.Resource;
import org.vault.base.domain.order.Orderable;
import org.vault.base.request.Path;

public interface VaultResourceTransformer extends Orderable {
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