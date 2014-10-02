package org.alloy.site.managed.resource.handler;

import org.alloy.metal.domain.Path;
import org.alloy.metal.resource._Resource;
import org.alloy.site.managed.resource.less.AlloyLessCompiler;
import org.alloy.site.resource.handler.AbstractAlloyResourceTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class LessToCssResourceTransformer extends AbstractAlloyResourceTransformer {
	@Autowired
	private AlloyLessCompiler lessCompiler;

	@Override
	public boolean canHandle(Path path, Resource resource) {
		if (_Resource.getPath(resource).endsWith(".less")) {
			return true;
		}
		return false;
	}

	@Override
	public Resource transform(Path path, Resource resource) {
		return lessCompiler.compile(resource);
	}
}