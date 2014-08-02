package org.vault.base.enviornment;

import org.vault.base.enumeration.AbstractAoEnumeration;
import org.vault.base.enumeration.VEnumerations;

public class EnvironmentType extends AbstractAoEnumeration {
	public static final EnvironmentType SHARED = VEnumerations.create("SHARED", "Shared", EnvironmentType.class);
	public static final EnvironmentType DEV = VEnumerations.create("DEV", "Development", EnvironmentType.class);
	public static final EnvironmentType QA = VEnumerations.create("QA", "Quality Assurance", EnvironmentType.class);
}