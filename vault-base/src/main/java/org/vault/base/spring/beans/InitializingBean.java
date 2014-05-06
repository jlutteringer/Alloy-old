package org.vault.base.spring.beans;

import org.vault.base.domain.Orderable;

public interface InitializingBean extends Orderable {
	public void initialize();
}
