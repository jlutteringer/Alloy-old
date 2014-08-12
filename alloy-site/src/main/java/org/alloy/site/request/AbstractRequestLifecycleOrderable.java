package org.alloy.site.request;

import org.alloy.metal.order.Phase;

public abstract class AbstractRequestLifecycleOrderable implements RequestLifecycleOrdeable {
	@Override
	public RequestLifecycle getOrderingObject() {
		return getRequestLifecycle();
	}

	@Override
	public Phase getSecondaryOrderingObject() {
		return getLifecyclePhase();
	}

	@Override
	public RequestLifecycle getRequestLifecycle() {
		return RequestLifecycle.DEFAULT;
	}

	@Override
	public Phase getLifecyclePhase() {
		return Phase.DEFAULT;
	}
}
