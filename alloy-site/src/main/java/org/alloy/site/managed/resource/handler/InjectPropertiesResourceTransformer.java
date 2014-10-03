package org.alloy.site.managed.resource.handler;

import org.alloy.core.managed.properties.AlloyPropertyPlaceholderConfigurer;
import org.alloy.metal.domain.Path;
import org.alloy.metal.resource._Resource;
import org.alloy.metal.utilities._Property;
import org.alloy.site.resource.handler.LocationRegisterableAbstractAlloyResourceTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class InjectPropertiesResourceTransformer extends LocationRegisterableAbstractAlloyResourceTransformer {
	@Autowired
	private AlloyPropertyPlaceholderConfigurer alloyPropertyPlaceholderConfigurer;

	@Override
	public Resource transform(Path path, Resource resource) {
		String resourceData = _Resource.stringify(resource);
		resourceData = _Property.resolve(resourceData, alloyPropertyPlaceholderConfigurer.getProperties());
		return _Resource.toResource(resourceData);
	}

	@Override
	public int getOrder() {
		return 1000;
	}
}