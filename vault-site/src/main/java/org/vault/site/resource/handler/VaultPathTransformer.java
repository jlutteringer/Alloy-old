package org.vault.site.resource.handler;

import org.alloy.metal.domain.Path;
import org.alloy.metal.order.Orderable;

public interface VaultPathTransformer extends Orderable {
	public boolean canHandle(Path path);

	public Path transform(Path path);
}