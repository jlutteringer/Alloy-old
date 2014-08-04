package org.alloy.site.resource.handler;

import org.alloy.metal.domain.Path;
import org.alloy.metal.order.Orderable;

public interface AlloyPathTransformer extends Orderable {
	public boolean canHandle(Path path);

	public Path transform(Path path);
}