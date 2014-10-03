package org.alloy.content.content.domain;

import org.alloy.metal.enumeration.AbstractExtendableEnumeration;
import org.alloy.metal.enumeration._ExtendableEnumeration;

public class ContentFieldType extends AbstractExtendableEnumeration {
	public static final ContentFieldType TEXT = _ExtendableEnumeration.create("TEXT", "Text", ContentFieldType.class);
}