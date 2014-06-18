package org.vault.base.domain.phase;

import org.vault.base.domain.Orderable;

public enum Phase implements Orderable {
	BOOTSTRAP, BEGIN, MIDDLE, END, SHUTDOWN;

	@Override
	public int getOrder() {
		return this.ordinal();
	}
}