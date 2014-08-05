package org.alloy.metal.order;

import org.alloy.metal.enumeration.AbstractExtendableEnumeration;
import org.alloy.metal.enumeration._ExtendableEnumeration;

public class Phase extends AbstractExtendableEnumeration implements Orderable {
	public static final Phase PRE_INITIALIZATION = _ExtendableEnumeration.create("PRE_INITIALIZATION", "Pre Initialization", Phase.class);
	public static final Phase INITIALIZATION = _ExtendableEnumeration.create("INITIALIZATION", "Initialization", Phase.class);
	public static final Phase POST_INITIALIZATION = _ExtendableEnumeration.create("POST_INITIALIZATION", "Post Initialization", Phase.class);

	public static final Phase NORMAL = _ExtendableEnumeration.create("NORMAL", "Normal", Phase.class);

	public static final Phase PRE_FINALIZATION = _ExtendableEnumeration.create("PRE_FINALIZATION", "Pre Finalization", Phase.class);
	public static final Phase FINALIZATION = _ExtendableEnumeration.create("FINALIZATION", "Finalization", Phase.class);
	public static final Phase POST_FINALIZATION = _ExtendableEnumeration.create("POST_FINALIZATION", "Post Finalization", Phase.class);

	public static final Phase DEFAULT = NORMAL;

	static {
		PRE_INITIALIZATION.order = 1000;
		INITIALIZATION.order = 2000;
		POST_INITIALIZATION.order = 3000;

		NORMAL.order = 4000;

		PRE_FINALIZATION.order = 5000;
		FINALIZATION.order = 6000;
		POST_FINALIZATION.order = 7000;
	}

	private int order = 0;

	@Override
	public int getOrder() {
		return order;
	}
}