package org.vault.base.domain.phase;

import org.vault.base.domain.Orderable;
import org.vault.base.enumeration.AbstractVEnumeration;
import org.vault.base.enumeration.VEnumerations;

public class Phase extends AbstractVEnumeration implements Orderable {
	public static final Phase PRE_INITIALIZATION = VEnumerations.create("PRE_INITIALIZATION", "Pre Initialization", Phase.class);
	public static final Phase INITIALIZATION = VEnumerations.create("INITIALIZATION", "Initialization", Phase.class);
	public static final Phase POST_INITIALIZATION = VEnumerations.create("POST_INITIALIZATION", "Post Initialization", Phase.class);

	public static final Phase NORMAL = VEnumerations.create("NORMAL", "Normal", Phase.class);

	public static final Phase PRE_FINALIZATION = VEnumerations.create("PRE_FINALIZATION", "Pre Finalization", Phase.class);
	public static final Phase FINALIZATION = VEnumerations.create("FINALIZATION", "Finalization", Phase.class);
	public static final Phase POST_FINALIZATION = VEnumerations.create("POST_FINALIZATION", "Post Finalization", Phase.class);

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