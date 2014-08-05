package org.alloy.site.request;

import org.alloy.metal.enumeration.AbstractExtendableEnumeration;
import org.alloy.metal.enumeration._ExtendableEnumeration;
import org.alloy.metal.order.Orderable;

public class RequestLifecycle extends AbstractExtendableEnumeration implements Orderable {
	public static final RequestLifecycle ALL = _ExtendableEnumeration.create("ALL", "Shared", RequestLifecycle.class);

	public static final RequestLifecycle PRE_SECURITY = _ExtendableEnumeration.create("PRE_SECURITY", "Pre Security Filters", RequestLifecycle.class);
	public static final RequestLifecycle SECURITY = _ExtendableEnumeration.create("SECURITY", "Security Filters", RequestLifecycle.class);
	public static final RequestLifecycle POST_SECURITY = _ExtendableEnumeration.create("POST_SECURITY", "Post Security Filters", RequestLifecycle.class);
	public static final RequestLifecycle DEFAULT = PRE_SECURITY;

	static {
		PRE_SECURITY.order = 2000;
		SECURITY.order = 3000;
		POST_SECURITY.order = 4000;
	}

	private int order = 0;

	@Override
	public int getOrder() {
		return order;
	}
}