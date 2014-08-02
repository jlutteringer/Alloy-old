package org.vault.base.classpath;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class _ClassPath {

	public static List<String> getClasspathEntries() {
		String[] classpathEntries = System.getProperty("java.class.path").split(File.pathSeparator);
		return Arrays.asList(classpathEntries);
	}
}
