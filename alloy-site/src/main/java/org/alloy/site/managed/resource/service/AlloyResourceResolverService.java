package org.alloy.site.managed.resource.service;

import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;

import org.alloy.core.managed.resource.AlloyResourceManager;
import org.alloy.metal.domain.Path;
import org.alloy.metal.order.Orderable;
import org.alloy.metal.spring.AlloyBean;
import org.alloy.site.resource.handler.AlloyResourceResolver;
import org.alloy.site.resource.handler.AlloyResourceTransformer;
import org.apache.logging.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

@Component
public class AlloyResourceResolverService extends AlloyBean {
	@Autowired
	protected List<AlloyResourceResolver> resolvers = Lists.newArrayList();

	@Autowired
	protected List<AlloyResourceTransformer> transformers = Lists.newArrayList();

	@Autowired
	private AlloyResourceManager resourceManager;

	@PostConstruct
	public void init() {
		Collections.sort(resolvers, Orderable.comparator());
		Collections.sort(transformers, Orderable.comparator());
	}

	public Resource getResource(Path path) {
		for (AlloyResourceResolver resolver : resolvers) {
			logger.printf(Level.TRACE, "Applying resolver [%s] to path [%s]", resolver, path);
			if (resolver.canHandle(path)) {
				Resource resource = resolver.getResource(path, getLocations());
				logger.printf(Level.DEBUG, "Resolved resource [%s]", resource);

				if (resource != null) {
					for (AlloyResourceTransformer transformer : transformers) {
						logger.printf(Level.TRACE, "Applying transformer [%s] to path [%s]", transformer, path);
						if (transformer.canHandle(path, resource)) {
							resource = transformer.transform(path, resource);
							logger.printf(Level.DEBUG, "Transformed resource [%s]", transformer);
						}
						else {
							logger.trace("Transformer does not apply to path");
						}
					}
				}

				return resource;
			}
			else {
				logger.trace("Resolver does not apply to path");
			}
		}

		return null;
	}

	private List<Resource> getLocations() {
		return resourceManager.getResources("resources");
	}
}
