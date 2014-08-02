package org.alloy.metal.environment;

import org.alloy.metal.enumeration.AbstractExtendableEnumeration;
import org.alloy.metal.enumeration._ExtendableEnumeration;

public class EnvironmentType extends AbstractExtendableEnumeration {
	public static final EnvironmentType SHARED = _ExtendableEnumeration.create("SHARED", "Shared", EnvironmentType.class);
	public static final EnvironmentType DEV = _ExtendableEnumeration.create("DEV", "Development", EnvironmentType.class);
	public static final EnvironmentType QA = _ExtendableEnumeration.create("QA", "Quality Assurance", EnvironmentType.class);
}