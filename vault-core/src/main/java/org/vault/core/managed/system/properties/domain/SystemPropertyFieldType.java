package org.vault.core.managed.system.properties.domain;

import org.vault.base.enumeration.AbstractVEnumeration;
import org.vault.base.enumeration.VEnumerations;

public class SystemPropertyFieldType extends AbstractVEnumeration {
	public static final SystemPropertyFieldType INT_TYPE = VEnumerations.create("INT_TYPE", "Integer value", SystemPropertyFieldType.class);
	public static final SystemPropertyFieldType LONG_TYPE = VEnumerations.create("LONG_TYPE", "Long value", SystemPropertyFieldType.class);
	public static final SystemPropertyFieldType DOUBLE_TYPE = VEnumerations.create("DOUBLE_TYPE", "Double value", SystemPropertyFieldType.class);
	public static final SystemPropertyFieldType BOOLEAN_TYPE = VEnumerations.create("BOOLEAN_TYPE", "Boolean value", SystemPropertyFieldType.class);
	public static final SystemPropertyFieldType STRING_TYPE = VEnumerations.create("STRING", "String value", SystemPropertyFieldType.class);
}