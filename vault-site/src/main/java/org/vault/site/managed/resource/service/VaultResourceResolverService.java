package org.vault.site.managed.resource.service;

import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;

import org.alloy.metal.domain.Path;
import org.alloy.metal.order.Orderable;
import org.alloy.metal.spring.AlloyBean;
import org.apache.logging.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.vault.core.managed.resource.VaultResourceManager;
import org.vault.site.resource.handler.VaultResourceResolver;
import org.vault.site.resource.handler.VaultResourceTransformer;

import com.google.common.collect.Lists;

@Component
public class VaultResourceResolverService extends AlloyBean {
	@Autowired
	protected List<VaultResourceResolver> resolvers = Lists.newArrayList();

	@Autowired
	protected List<VaultResourceTransformer> transformers = Lists.newArrayList();

	@Autowired
	private VaultResourceManager resourceManager;

	@PostConstruct
	public void init() {
		Collections.sort(resolvers, Orderable.comparator());
		Collections.sort(transformers, Orderable.comparator());
	}

	public Resource getResource(Path path) {
		for (VaultResourceResolver resolver : resolvers) {
			logger.printf(Level.DEBUG, "Applying resolver [%s] to path [%s]", resolver, path);
			if (resolver.canHandle(path)) {
				Resource resource = resolver.getResource(path, getLocations());
				logger.printf(Level.DEBUG, "Resolved resource [%s]", resource);

				if (resource != null) {
					for (VaultResourceTransformer transformer : transformers) {
						logger.printf(Level.DEBUG, "Applying transformer [%s] to path [%s]", transformer, path);
						if (transformer.canHandle(path, resource)) {
							resource = transformer.transform(path, resource);
							logger.printf(Level.DEBUG, "Transformed resource [%s]", transformer);
						}
						else {
							logger.debug("Transformer does not apply to path");
						}
					}
				}

				return resource;
			}
			else {
				logger.debug("Resolver does not apply to path");
			}
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	private List<Resource> getLocations() {
		return (List<Resource>) (List<?>) resourceManager.getResources("resources");
	}
}
