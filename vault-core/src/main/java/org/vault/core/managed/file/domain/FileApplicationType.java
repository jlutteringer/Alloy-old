package org.vault.core.managed.file.domain;

import org.vault.base.enumeration.AbstractAoEnumeration;
import org.vault.base.enumeration.VEnumerations;

public class FileApplicationType extends AbstractAoEnumeration {
	public static final FileApplicationType ALL = VEnumerations.create("ALL", "All", FileApplicationType.class);
	public static final FileApplicationType IMAGE = VEnumerations.create("IMAGE", "Image", FileApplicationType.class);
	public static final FileApplicationType STATIC = VEnumerations.create("STATIC", "Static Assets", FileApplicationType.class);
	public static final FileApplicationType SITE_MAP = VEnumerations.create("SITE_MAP", "Site Map", FileApplicationType.class);
}