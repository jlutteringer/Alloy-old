package org.alloy.site.managed.resource.handler;

import java.util.Set;

import org.alloy.core.managed.properties.AlloyPropertyPlaceholderConfigurer;
import org.alloy.metal.domain.Path;
import org.alloy.metal.resource._Resource;
import org.alloy.metal.utilities._Property;
import org.alloy.site.resource.handler.AbstractAlloyResourceTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.google.common.collect.Sets;

@Component
public class InjectPropertiesResourceTransformer extends AbstractAlloyResourceTransformer {
	private Set<String> resourceLocations = Sets.newHashSet();

	@Autowired
	private AlloyPropertyPlaceholderConfigurer alloyPropertyPlaceholderConfigurer;

	@Override
	public boolean canHandle(Path path, Resource resource) {
		if (resourceLocations.contains(path.getPath())) {
			return true;
		}
		return false;
	}

	@Override
	public Resource transform(Path path, Resource resource) {
		String resourceData = _Resource.stringify(resource);
		resourceData = _Property.resolve(resourceData, alloyPropertyPlaceholderConfigurer.getProperties());
		return _Resource.toResource(resourceData);
	}

	public void register(String resourceLocation) {
		resourceLocations.add(resourceLocation);
	}

	@Override
	public int getOrder() {
		return 1000;
	}
}