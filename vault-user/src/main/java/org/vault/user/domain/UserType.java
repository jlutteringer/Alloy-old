package org.vault.user.domain;

import org.vault.base.enumeration.AbstractAoEnumeration;
import org.vault.base.enumeration.VEnumerations;

public class UserType extends AbstractAoEnumeration {
	public static final UserType NORMAL = VEnumerations.create("NORMAL", "Normal", UserType.class);
}