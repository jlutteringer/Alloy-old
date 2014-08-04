package org.alloy.core.managed.file;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.alloy.core.file.FileApplicationType;
import org.alloy.core.file.FileServiceException;
import org.alloy.core.file.FileWorkArea;
import org.alloy.metal.spring.AlloyBean;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
public class FileSystemFileServiceProvider extends AlloyBean implements FileServiceProvider {
	@Value("${asset.server.file.system.path}")
	protected String fileSystemBaseDirectory;

	@Value("${asset.server.max.generated.file.system.directories}")
	protected int maxGeneratedDirectoryDepth;

	@Override
	public File getResource(String url) {
		return getResource(url, FileApplicationType.ALL);
	}

	@Override
	public File getResource(String url, FileApplicationType applicationType) {
		String fileName = buildResourceName(url);
		String filePath = FilenameUtils.normalize(fileSystemBaseDirectory + File.separator + fileName);
		return new File(filePath);
	}

	@Override
	public void addOrUpdateResources(FileWorkArea area, List<File> files, boolean removeResourcesFromWorkArea) {
		for (File srcFile : files) {
			if (!srcFile.getAbsolutePath().startsWith(area.getFilePathLocation())) {
				throw new FileServiceException("Attempt to update file " + srcFile.getAbsolutePath() +
						" that is not in the passed in WorkArea " + area.getFilePathLocation());
			}

			String fileName = srcFile.getAbsolutePath().substring(area.getFilePathLocation().length());

			// before building the resource name, convert the file path to a url-like path
			String url = FilenameUtils.separatorsToUnix(fileName);
			String resourceName = buildResourceName(url);
			String destinationFilePath = FilenameUtils.normalize(fileSystemBaseDirectory + File.separator + resourceName);
			File destFile = new File(destinationFilePath);
			if (!destFile.getParentFile().exists()) {
				destFile.getParentFile().mkdirs();
			}

			try {
				if (removeResourcesFromWorkArea) {
					if (destFile.exists()) {
						FileUtils.deleteQuietly(destFile);
					}
					FileUtils.moveFile(srcFile, destFile);
				} else {
					FileUtils.copyFile(srcFile, destFile);
				}
			} catch (IOException ioe) {
				throw new FileServiceException("Error copying resource named " + fileName + " from workArea " +
						area.getFilePathLocation() + " to " + resourceName, ioe);
			}
		}
	}

	@Override
	public boolean removeResource(String name) {
		String resourceName = buildResourceName(name);
		String filePathToRemove = FilenameUtils.normalize(fileSystemBaseDirectory + File.separator + resourceName);
		File fileToRemove = new File(filePathToRemove);
		return fileToRemove.delete();
	}

	/**
	* Stores the file on the file-system by performing an MD5 hash of the
	* the passed in fileName
	*
	* To ensure that files can be stored and accessed in an efficient manner, the
	* system creates directories based on the characters in the hash.
	*
	* For example, if the URL is /product/myproductimage.jpg, then the MD5 would be
	* 35ec52a8dbd8cf3e2c650495001fe55f resulting in the following file on the filesystem
	* {assetFileSystemPath}/35/ec/myproductimage.jpg.
	*
	* The hash for the filename will include a beginning slash before performing the MD5. This
	* is done largely for backward compatibility with similar functionality in BLC 3.0.0.
	*
	* This algorithm has the following benefits:
	* - Efficient file-system storage with
	* - Balanced tree of files that supports 10 million files
	*
	* If support for more files is needed, implementors should consider one of the following approaches:
	* 1. Overriding the maxGeneratedFileSystemDirectories property from its default of 2 to 3
	* 2. Overriding this method to introduce an alternate approach
	*
	* @param url The URL used to represent an asset for which a name on the fileSystem is desired.
	*
	* @return
	*/
	protected String buildResourceName(String url) {
		// Create directories based on hash
		String fileHash = null;
		// Intentionally not using File.separator here since URLs should always end with /
		if (!url.startsWith("/")) {
			fileHash = DigestUtils.md5DigestAsHex(("/" + url).getBytes());
		} else {
			fileHash = DigestUtils.md5DigestAsHex(url.getBytes());
		}

		String resourceName = "";
		for (int i = 0; i < maxGeneratedDirectoryDepth; i++) {
			if (i == 4) {
				logger.warn("Property maxGeneratedDirectoryDepth set to high, ignoring values past 4 - value set to" +
						maxGeneratedDirectoryDepth);
				break;
			}
			resourceName = FilenameUtils.concat(resourceName, fileHash.substring(i * 2, (i + 1) * 2));
		}

		// use the filename from the URL which is everything after the last slash
		return FilenameUtils.concat(resourceName, FilenameUtils.getName(url));
	}
}
