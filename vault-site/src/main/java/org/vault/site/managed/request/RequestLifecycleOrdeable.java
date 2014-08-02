package org.vault.site.managed.request;

import org.alloy.metal.order.Phase;
import org.alloy.metal.order.ObjectOrderable.BiObjectOrderable;

public interface RequestLifecycleOrdeable extends BiObjectOrderable<RequestLifecycle, Phase> {
	@Override
	public default RequestLifecycle getOrderingObject() {
		return getRequestLifecycle();
	}

	@Override
	public default Phase getSecondaryOrderingObject() {
		return getLifecyclePhase();
	}

	public default RequestLifecycle getRequestLifecycle() {
		return RequestLifecycle.PRE_SECURITY;
	}

	public default Phase getLifecyclePhase() {
		return Phase.NORMAL;
	}
}