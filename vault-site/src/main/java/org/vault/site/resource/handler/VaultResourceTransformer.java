package org.vault.site.resource.handler;

import org.springframework.core.Ordered;
import org.springframework.core.io.Resource;

public interface VaultResourceTransformer extends Ordered {
	/**
	* @param path
	* @return booelean determining whether or not this handler is able to handle the given request
	*/
	public boolean canHandle(String path, Resource resource);

	/**
	* Attempts to retrive the requested resource from cache. If not cached, generates the resource, caches it,
	* and then returns it
	*
	* @param request
	* @param location
	* @return the generated resource
	*/
	public Resource transform(String path, Resource resource);
}
