package org.vault.base.file;

import java.io.File;

import org.vault.base.collections.iterable._Iterable;

public class VFiles {
	public static Iterable<String> getPaths(Iterable<File> files) {
		return _Iterable.transform(files, (file) -> file.getPath());
	}
}
