package org.vault.site.managed.resource.handler;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.vault.site.resource.GeneratedResource;
import org.vault.site.resource.handler.AbstractVaultResourceHandler;

@Component
public class BaseVaultResourceHandler extends AbstractVaultResourceHandler {
	@Override
	public boolean canHandle(String path) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Resource getFileContents(String path, List<Resource> locations) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isCachedResourceExpired(GeneratedResource cachedResource, String path, List<Resource> locations) {
		// TODO Auto-generated method stub
		return false;
	}
}
