package org.vault.base.reflection;

public class VClasses {
	public static void intialize(Class<?> clazz) {
		try {
			Class.forName(clazz.getName());
		} catch (ClassNotFoundException e) {
			// Do nothing
		}
	}
}
