package org.alloy.site.request;

import org.alloy.metal.order.ObjectOrderable.BiObjectOrderable;
import org.alloy.metal.order.Phase;

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
		return RequestLifecycle.DEFAULT;
	}

	public default Phase getLifecyclePhase() {
		return Phase.DEFAULT;
	}
}