package org.vault.core.file;

import org.alloy.metal.enumeration.AbstractExtendableEnumeration;
import org.alloy.metal.enumeration._ExtendableEnumeration;

public class FileApplicationType extends AbstractExtendableEnumeration {
	public static final FileApplicationType ALL = _ExtendableEnumeration.create("ALL", "All", FileApplicationType.class);
	public static final FileApplicationType IMAGE = _ExtendableEnumeration.create("IMAGE", "Image", FileApplicationType.class);
	public static final FileApplicationType STATIC = _ExtendableEnumeration.create("STATIC", "Static Assets", FileApplicationType.class);
	public static final FileApplicationType SITE_MAP = _ExtendableEnumeration.create("SITE_MAP", "Site Map", FileApplicationType.class);
}