package org.alloy.metal.spring;

import org.alloy.metal.order.Orderable;

public abstract class AlloyOrderableBean extends AlloyBean implements Orderable {
	@Override
	public int getOrder() {
		return Orderable.DEFAULT_ORDER;
	}
}