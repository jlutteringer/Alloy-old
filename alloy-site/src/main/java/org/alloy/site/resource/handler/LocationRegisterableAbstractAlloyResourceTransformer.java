package org.alloy.site.resource.handler;

import java.util.Set;

import org.alloy.metal.domain.Path;
import org.springframework.core.io.Resource;

import com.google.common.collect.Sets;

public abstract class LocationRegisterableAbstractAlloyResourceTransformer extends AbstractAlloyResourceTransformer {
	private Set<String> resourceLocations = Sets.newHashSet();

	@Override
	public boolean canHandle(Path path, Resource resource) {
		if (this.getResourceLocations().contains(path.getPath())) {
			return true;
		}
		return false;
	}

	public void register(String resourceLocation) {
		resourceLocations.add(resourceLocation);
	}

	protected Set<String> getResourceLocations() {
		return resourceLocations;
	}
}
