package org.vault.user.domain;

import org.vault.base.enumeration.AbstractAoEnumeration;
import org.vault.base.enumeration.VEnumerations;

public class PermissionType extends AbstractAoEnumeration {
	public static final PermissionType READ = VEnumerations.create("READ", "Read", PermissionType.class);
	public static final PermissionType CREATE = VEnumerations.create("CREATE", "Create", PermissionType.class);
	public static final PermissionType UPDATE = VEnumerations.create("UPDATE", "Update", PermissionType.class);
	public static final PermissionType DELETE = VEnumerations.create("DELETE", "Delete", PermissionType.class);
	public static final PermissionType ALL = VEnumerations.create("ALL", "All", PermissionType.class);
	public static final PermissionType OTHER = VEnumerations.create("OTHER", "Other", PermissionType.class);
}