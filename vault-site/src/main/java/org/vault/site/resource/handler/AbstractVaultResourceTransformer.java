package org.vault.site.resource.handler;

import org.vault.base.spring.beans.VaultBean;

public abstract class AbstractVaultResourceTransformer extends VaultBean implements VaultResourceTransformer {
	public static final int DEFAULT_ORDER = 10000;

	@Override
	public int getOrder() {
		return DEFAULT_ORDER;
	}
}