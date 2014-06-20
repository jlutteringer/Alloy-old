package org.vault.site.templates;

import java.io.InputStream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.thymeleaf.TemplateProcessingParameters;
import org.thymeleaf.resourceresolver.IResourceResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

public class VaultTemplateResolver extends TemplateResolver {
	@Autowired
	private ApplicationContext applicationContext;

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
			return applicationContext.getClassLoader().getResourceAsStream(resourceName);
		}
	}
}
