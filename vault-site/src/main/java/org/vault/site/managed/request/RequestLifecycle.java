package org.vault.site.managed.request;

import org.vault.base.domain.order.Orderable;
import org.vault.base.enumeration.AbstractVEnumeration;
import org.vault.base.enumeration.VEnumerations;

public class RequestLifecycle extends AbstractVEnumeration implements Orderable {
	public static final RequestLifecycle ALL = VEnumerations.create("ALL", "Shared", RequestLifecycle.class);

	public static final RequestLifecycle PRE_SECURITY = VEnumerations.create("PRE_SECURITY", "Pre Security Filters", RequestLifecycle.class);
	public static final RequestLifecycle SECURITY = VEnumerations.create("SECURITY", "Security Filters", RequestLifecycle.class);
	public static final RequestLifecycle POST_SECURITY = VEnumerations.create("POST_SECURITY", "Post Security Filters", RequestLifecycle.class);

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