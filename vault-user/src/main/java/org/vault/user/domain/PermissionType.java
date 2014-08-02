package org.vault.user.domain;

import org.alloy.metal.enumeration.AbstractExtendableEnumeration;
import org.alloy.metal.enumeration._ExtendableEnumeration;

public class PermissionType extends AbstractExtendableEnumeration {
	public static final PermissionType READ = _ExtendableEnumeration.create("READ", "Read", PermissionType.class);
	public static final PermissionType CREATE = _ExtendableEnumeration.create("CREATE", "Create", PermissionType.class);
	public static final PermissionType UPDATE = _ExtendableEnumeration.create("UPDATE", "Update", PermissionType.class);
	public static final PermissionType DELETE = _ExtendableEnumeration.create("DELETE", "Delete", PermissionType.class);
	public static final PermissionType ALL = _ExtendableEnumeration.create("ALL", "All", PermissionType.class);
	public static final PermissionType OTHER = _ExtendableEnumeration.create("OTHER", "Other", PermissionType.class);
}