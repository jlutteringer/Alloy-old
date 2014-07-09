package org.vault.web.request;

import org.vault.base.enumeration.AbstractVEnumeration;
import org.vault.base.enumeration.VEnumerations;

public class RequestLifecycle extends AbstractVEnumeration {
	public static final RequestLifecycle ALL = VEnumerations.create("ALL", "Shared", RequestLifecycle.class);
	public static final RequestLifecycle PRE_SECURITY = VEnumerations.create("PRE_SECURITY", "Pre Security Filters", RequestLifecycle.class);
	public static final RequestLifecycle POST_SECURITY = VEnumerations.create("POST_SECURITY", "Post Security Filters", RequestLifecycle.class);
}