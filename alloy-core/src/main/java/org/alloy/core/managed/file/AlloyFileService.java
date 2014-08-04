package org.alloy.core.managed.file;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import org.alloy.core.file.FileWorkArea;

public interface AlloyFileService {
	/**
	* Create a file work area that can be used for further operations.
	* @return
	*/
	public FileWorkArea initializeWorkArea();

	/**
	* Closes the passed in work area. This method will delete all items contained in the work area. Future calls
	* using this WorkArea will cause a RuntimeError
	* @param Work Area
	*/
	public void closeWorkArea(FileWorkArea workArea);

	/**
	* Returns a File representing the passed in name. This method will always access the file via the FileProvider
	* which might be a remote operation.
	*
	* @param name - fully qualified path to the resource
	* @return
	*/
	public File getResource(String name);

	/**
	* Returns a File representing the resource. This method first checks the local temporary directory for the file.
	* If it exists and has been modified within the timeout parameter, it will be returned. Otherwise, this method
	* will make a call to {@link #getResource(String)}.
	*
	* If the timeout parameter is null then if the resource exists locally, it will be returned.
	*
	* @param name - fully qualified path to the resource
	* @param timeout - timeframe that the temporary file is considered valid
	* @return
	*/
	public File getResource(String name, Long timeout);

	/**
	* Checks for a resource in the temporary directory of the file-system. Will check for a site-specific file.
	*
	* @param fullUrl
	* @return
	*/
	public File getLocalResource(String fullUrl);

	/**
	* Checks for a resource in the temporary directory of the file-system. Will check for a global (e.g. not site
	* specific file).
	*
	* @param fullUrl
	* @return
	*/
	public File getSharedLocalResource(String fullUrl);

	/**
	* Returns true if the resource is available on the classpath.
	* @param name
	* @return
	*/
	public boolean checkForResourceOnClassPath(String name);

	/**
	* Allows assets to be included in the Java classpath.
	*
	* This method was designed to support an internal Broadleaf use case and may not have general applicability
	* beyond that. For Broadleaf demo sites, many of the product images are shared across the demo sites.
	*
	* Rather than copy those images, they are stored in a Jar file and shared by all of the sites.
	*
	* @param name - fully qualified path to the resource
	* @return
	*/
	public InputStream getClasspathResource(String name);

	/**
	* Removes the resource from the configured FileProvider
	*
	* @param name - fully qualified path to the resource
	* @param applicationType - The type of file being accessed
	*/
	public boolean removeResource(String name);

	/**
	* Takes in a temporary work area and a single File and copies that files to
	* the configured FileProvider's permanent storage.
	*
	* Passing in removeFilesFromWorkArea to true allows for more efficient file processing
	* when using a local file system as it performs a move operation instead of a copy.
	*
	* @param workArea
	* @param fileName
	* @param removeFilesFromWorkArea
	*/
	public void addOrUpdateResource(FileWorkArea workArea, File file, boolean removeFilesFromWorkArea);

	/**
	* Takes in a temporary work area and copies all of the files to the configured FileProvider's permanent storage.
	*
	* Passing in removeFilesFromWorkArea to true allows for more efficient file processing
	* when using a local file system as it performs a move operation instead of a copy.
	*
	* @param workArea
	* @param removeFilesFromWorkArea
	*/
	public void addOrUpdateResources(FileWorkArea workArea, boolean removeFilesFromWorkArea);

	/**
	* Takes in a temporary work area and a list of Files and copies them to
	* the configured FileProvider's permanent storage.
	*
	* Passing in removeFilesFromWorkArea to true allows for more efficient file processing
	* when using a local file system as it performs a move operation instead of a copy.
	*
	* @param workArea
	* @param fileNames
	* @param removeFilesFromWorkArea
	*/
	public void addOrUpdateResources(FileWorkArea workArea, List<File> files, boolean removeFilesFromWorkArea);
}
