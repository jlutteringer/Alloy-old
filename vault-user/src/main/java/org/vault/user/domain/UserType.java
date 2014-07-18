package org.vault.user.domain;

import org.vault.base.enumeration.AbstractVEnumeration;
import org.vault.base.enumeration.VEnumerations;

public class UserType extends AbstractVEnumeration {
	public static final UserType NORMAL = VEnumerations.create("NORMAL", "Normal", UserType.class);
}