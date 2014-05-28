package org.vault.persistence.test;

import org.vault.base.enumeration.VEnumerations;
import org.vault.base.enviornment.EnvironmentType;

public class ExtendedEnvironmentType extends EnvironmentType {
	public static final EnvironmentType EXTENDED = VEnumerations.create("EXTENDED", "Extended", ExtendedEnvironmentType.class);
}