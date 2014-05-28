package org.vault.base.enviornment;

import java.util.List;

import org.vault.base.enumeration.AbstractVEnumeration;
import org.vault.base.enumeration.VEnumerations;

import com.google.common.collect.Lists;

public class EnvironmentType extends AbstractVEnumeration {
	private static final List<EnvironmentType> values = Lists.newArrayList();

	public static final EnvironmentType SHARED = VEnumerations.create("SHARED", "Shared", EnvironmentType.class, values);
	public static final EnvironmentType DEV = VEnumerations.create("DEV", "Development", EnvironmentType.class, values);
	public static final EnvironmentType QA = VEnumerations.create("QA", "Quality Assurance", EnvironmentType.class, values);

	public static List<EnvironmentType> getValues() {
		return values;
	}
}