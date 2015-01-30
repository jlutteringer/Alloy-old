package org.alloy.angular.managed.resource.handler;

import org.alloy.metal.collections.lists._Lists;
import org.alloy.metal.domain.Path;
import org.alloy.metal.resource._Resource;
import org.alloy.site.resource.configuration.AbstractResourceConfiguration;
import org.alloy.site.resource.handler.LocationRegisterableAbstractAlloyResourceTransformer;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class DirectiveTemplatingResourceTransformer extends LocationRegisterableAbstractAlloyResourceTransformer {
	@Override
	public Resource transform(Path path, Resource resource) {
		String resourceContents = _Resource.stringify(resource);
		return resource;
	}

	@Override
	public int getOrder() {
		return 500;
	}

	public static class AngularResourceConfiguration extends AbstractResourceConfiguration {
		public static AngularResourceConfiguration resolveDirectiveTemplates(String... resourceLocations) {
			AngularResourceConfiguration configuration = new AngularResourceConfiguration();
			configuration.getResourceLocations().addAll(_Lists.list(resourceLocations));
			configuration.setResolveDirectiveTemplates(true);
			return configuration;
		}

		public boolean isResolveDirectiveTemplates() {
			return resolveDirectiveTemplates;
		}

		public void setResolveDirectiveTemplates(boolean resolveDirectiveTemplates) {
			this.resolveDirectiveTemplates = resolveDirectiveTemplates;
		}

		private boolean resolveDirectiveTemplates = false;
	}
}