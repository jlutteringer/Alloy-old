package org.vault.base.file;

import java.io.File;

import org.vault.base.collections.iterable.VIterables;

public class VFiles {
	public static Iterable<String> getPaths(Iterable<File> files) {
		return VIterables.transform(files, (file) -> file.getPath());
	}
}
