package org.vault.base.spring.beans.ordered;

import org.vault.base.domain.phase.Phase;

public interface PhaseOrderedBean extends OrderableBean {
	public Phase getPhase();

	@Override
	public default int getOrder() {
		return this.getPhase().getOrder();
	}
}
