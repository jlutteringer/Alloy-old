package org.alloy.content.domain;

import org.alloy.metal.enumeration.AbstractExtendableEnumeration;
import org.alloy.metal.enumeration._ExtendableEnumeration;

public class ContentRelationType extends AbstractExtendableEnumeration {
	public static final ContentRelationType ONE_TO_ONE = _ExtendableEnumeration.create("ONE_TO_ONE", "One to One", ContentRelationType.class);
	public static final ContentRelationType ONE_TO_MANY = _ExtendableEnumeration.create("ONE_TO_MANY", "One to Many", ContentRelationType.class);
	public static final ContentRelationType MANY_TO_ONE = _ExtendableEnumeration.create("MANY_TO_ONE", "Many to One", ContentRelationType.class);
	public static final ContentRelationType MANY_TO_MANY = _ExtendableEnumeration.create("MANY_TO_MANY", "Many to Many", ContentRelationType.class);
}