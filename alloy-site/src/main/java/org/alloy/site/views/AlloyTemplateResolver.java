package org.alloy.site.views;

import java.io.InputStream;

import javax.annotation.PostConstruct;

import org.alloy.core.managed.resource.AlloyResourceManager;
import org.alloy.metal.resource._Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.thymeleaf.TemplateProcessingParameters;
import org.thymeleaf.resourceresolver.IResourceResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

public class AlloyTemplateResolver extends TemplateResolver {
	@Autowired
	private AlloyResourceManager resourceManager;

	public AlloyTemplateResolver() {
		super();
	}

	@PostConstruct
	private void init() {
		this.setResourceResolver(new AlloyResourceResolver());
	}

	public class AlloyResourceResolver implements IResourceResolver {
		@Override
		public String getName() {
			return "ALLOY";
		}

		@Override
		public InputStream getResourceAsStream(TemplateProcessingParameters templateProcessingParameters, String resourceName) {
			Resource resource = resourceManager.getResource(resourceName);
			if (resource != null) {
				return _Resource.getInputStream(resourceManager.getResource(resourceName));
			}

			return null;
		}
	}
}
