package org.alloy.site.managed.resource.handler;

import java.util.List;

import org.alloy.metal.domain.Path;
import org.alloy.site.managed.resource.service.ResourceAliasingService;
import org.alloy.site.resource.handler.AbstractAlloyResourceResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class AliasedResourceResolver extends AbstractAlloyResourceResolver {
	@Autowired
	private ResourceAliasingService resourceAliasingService;

	@Override
	public boolean canHandle(Path path) {
		return resourceAliasingService.isAlias(path);
	}

	@Override
	public Resource getFileContents(Path path, List<Resource> locations) {
		return resourceAliasingService.resolve(path.getPath());
	}

	@Override
	public int getOrder() {
		return 1000;
	}
}