package org.vault.core.managed.file.domain;

import java.io.File;

public class FileWorkArea {
	protected String filePathLocation;

	/**
	* Gets the file path location representing this work area ending with an appropriate system-specific separator
	* @return
	*/
	public String getFilePathLocation() {
		if (!filePathLocation.endsWith(File.separator)) {
			return filePathLocation + File.separator;
		} else {
			return filePathLocation;
		}
	}

	public void setFilePathLocation(String filePathLocation) {
		if (!filePathLocation.endsWith(File.separator)) {
			this.filePathLocation = filePathLocation + File.separator;
		} else {
			this.filePathLocation = filePathLocation;
		}
	}
}
