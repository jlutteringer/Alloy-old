package org.vault.site.managed.resource.service;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.vault.base.spring.beans.VaultBean;

@Service
public class ResourceBundlingServiceImpl extends VaultBean implements ResourceBundlingService {

	@Override
	public boolean hasBundleForResource(String path) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Resource getBundleForResource(String path, List<Resource> locations) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Collection<Resource>> getBundles() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String registerBundle(String bundleName, List<String> files, VaultResourceHttpRequestHandler handler) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}
}
