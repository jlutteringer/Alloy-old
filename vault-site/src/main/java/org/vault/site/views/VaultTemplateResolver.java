package org.vault.site.views;

import java.io.InputStream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.thymeleaf.TemplateProcessingParameters;
import org.thymeleaf.resourceresolver.IResourceResolver;
import org.thymeleaf.templateresolver.TemplateResolver;
import org.vault.base.utilities.resources.VResources;
import org.vault.core.managed.resource.VaultClasspathResourceManager;

public class VaultTemplateResolver extends TemplateResolver {
	@Autowired
	private VaultClasspathResourceManager resourceManager;

	public VaultTemplateResolver() {
		super();
	}

	@PostConstruct
	private void init() {
		this.setResourceResolver(new VaultResourceResolver());
	}

	public class VaultResourceResolver implements IResourceResolver {
		@Override
		public String getName() {
			return "VAULT";
		}

		@Override
		public InputStream getResourceAsStream(TemplateProcessingParameters templateProcessingParameters, String resourceName) {
			Resource resource = resourceManager.getResource(resourceName);
			if (resource != null) {
				return VResources.getInputStream(resourceManager.getResource(resourceName));
			}

			return null;
		}
	}
}
