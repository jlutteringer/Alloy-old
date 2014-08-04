package org.alloy.user.domain;

import org.alloy.metal.enumeration.AbstractExtendableEnumeration;
import org.alloy.metal.enumeration._ExtendableEnumeration;

public class UserType extends AbstractExtendableEnumeration {
	public static final UserType NORMAL = _ExtendableEnumeration.create("NORMAL", "Normal", UserType.class);
}