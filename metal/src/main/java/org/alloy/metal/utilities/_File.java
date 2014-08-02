package org.alloy.metal.utilities;

import java.io.File;

import org.alloy.metal.collections.iterable._Iterable;

public class _File {
	public static Iterable<String> getPaths(Iterable<File> files) {
		return _Iterable.transform(files, (file) -> file.getPath());
	}
}
