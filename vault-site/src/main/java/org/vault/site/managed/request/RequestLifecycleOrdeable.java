package org.vault.site.managed.request;

import org.vault.base.domain.order.ObjectOrderable.BiObjectOrderable;
import org.vault.base.domain.phase.Phase;

public interface RequestLifecycleOrdeable extends BiObjectOrderable<RequestLifecycle, Phase> {
	@Override
	public default RequestLifecycle getPhase() {
		return getRequestLifecycle();
	}

	@Override
	public default Phase getSecondaryPhase() {
		return getLifecyclePhase();
	}

	public default RequestLifecycle getRequestLifecycle() {
		return RequestLifecycle.PRE_SECURITY;
	}

	public default Phase getLifecyclePhase() {
		return Phase.NORMAL;
	}
}