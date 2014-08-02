package org.vault.core.managed.system.properties.domain;

import org.alloy.metal.enumeration.AbstractExtendableEnumeration;
import org.alloy.metal.enumeration._ExtendableEnumeration;

public class SystemPropertyFieldType extends AbstractExtendableEnumeration {
	public static final SystemPropertyFieldType INT_TYPE = _ExtendableEnumeration.create("INT_TYPE", "Integer value", SystemPropertyFieldType.class);
	public static final SystemPropertyFieldType LONG_TYPE = _ExtendableEnumeration.create("LONG_TYPE", "Long value", SystemPropertyFieldType.class);
	public static final SystemPropertyFieldType DOUBLE_TYPE = _ExtendableEnumeration.create("DOUBLE_TYPE", "Double value", SystemPropertyFieldType.class);
	public static final SystemPropertyFieldType BOOLEAN_TYPE = _ExtendableEnumeration.create("BOOLEAN_TYPE", "Boolean value", SystemPropertyFieldType.class);
	public static final SystemPropertyFieldType STRING_TYPE = _ExtendableEnumeration.create("STRING", "String value", SystemPropertyFieldType.class);
}