package org.vault.base.spring.beans;

import org.vault.base.domain.order.Orderable;

public abstract class VaultOrderableBean extends VaultBean implements Orderable {
	@Override
	public int getOrder() {
		return Orderable.DEFAULT_ORDER;
	}
}