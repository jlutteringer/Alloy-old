package org.vault.site.managed.resource.service;

import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.vault.base.domain.order.Orderable;
import org.vault.base.request.Path;
import org.vault.base.spring.beans.VaultBean;
import org.vault.core.managed.resource.VaultClasspathResourceManager;
import org.vault.site.resource.handler.VaultPathTransformer;
import org.vault.site.resource.handler.VaultResourceResolver;
import org.vault.site.resource.handler.VaultResourceTransformer;

import com.google.common.collect.Lists;

@Component
public class VaultResourceResolverService extends VaultBean {
	@Autowired
	protected List<VaultResourceResolver> resolvers = Lists.newArrayList();

	@Autowired
	protected List<VaultResourceTransformer> transformers = Lists.newArrayList();

	@Autowired
	protected List<VaultPathTransformer> pathTransformers = Lists.newArrayList();

	@Autowired
	private VaultClasspathResourceManager resourceManager;

	@PostConstruct
	public void init() {
		Collections.sort(resolvers, Orderable.comparator());
		Collections.sort(transformers, Orderable.comparator());
		Collections.sort(pathTransformers, Orderable.comparator());
	}

	public Resource getResource(Path path) {
		for (VaultPathTransformer transformer : pathTransformers) {
			if (transformer.canHandle(path)) {
				path = transformer.transform(path);
			}
		}

		for (VaultResourceResolver resolver : resolvers) {
			if (resolver.canHandle(path)) {
				Resource resource = resolver.getResource(path, getLocations());
				for (VaultResourceTransformer transformer : transformers) {
					if (transformer.canHandle(path, resource)) {
						resource = transformer.transform(path, resource);
					}
				}
				return resource;
			}
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	private List<Resource> getLocations() {
		return (List<Resource>) (List<?>) resourceManager.getLocations("resources");
	}
}
