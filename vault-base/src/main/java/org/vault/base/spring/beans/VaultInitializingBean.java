package org.vault.base.spring.beans;

import org.vault.base.domain.Orderable;

public interface VaultInitializingBean extends Orderable {
	public void initialize();
}
