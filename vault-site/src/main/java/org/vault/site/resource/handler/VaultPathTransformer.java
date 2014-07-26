package org.vault.site.resource.handler;

import org.vault.base.domain.order.Orderable;
import org.vault.base.request.Path;

public interface VaultPathTransformer extends Orderable {
	public boolean canHandle(Path path);

	public Path transform(Path path);
}