package org.vault.base.enviornment;

import org.vault.base.enumeration.AbstractVEnumeration;
import org.vault.base.enumeration.VEnumerations;

public class EnvironmentType extends AbstractVEnumeration {
	public static final EnvironmentType DEV = VEnumerations.create("DEV", "Development", EnvironmentType.class);
	public static final EnvironmentType QA = VEnumerations.create("QA", "Quality Assurance", EnvironmentType.class);

}