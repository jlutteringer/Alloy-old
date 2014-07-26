package org.vault.base.request;

public class Path {
	private String rawPath;
	private String path;

	public static Path of(String stringPath) {
		Path path = new Path();
		path.path = stringPath;
		path.rawPath = stringPath;
		return path;
	}

	public String getRawPath() {
		return rawPath;
	}

	public void setRawPath(String rawPath) {
		this.rawPath = rawPath;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
