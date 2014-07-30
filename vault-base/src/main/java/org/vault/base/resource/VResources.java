package org.vault.base.resource;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.util.InMemoryResource;
import org.vault.base.classpath.VClassPath;
import org.vault.base.closeable.VCloseables;
import org.vault.base.collections.iterable.VIterables;
import org.vault.base.collections.lists.VLists;
import org.vault.base.file.VFiles;
import org.vault.base.spring.applicationContext.Spring;
import org.vault.base.utilities.exception.Exceptions;
import org.vault.base.utilities.function.ExceptionConsumer;

import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import com.google.common.io.ByteStreams;

public class VResources {
	private static final Logger logger = LogManager.getLogger(VResources.class);

	public static boolean exists(Resource resource) {
		if (resource != null && resource.exists()) {
			return true;
		}
		return false;
	}

	public static Resource findExistingRelative(Resource location, String relativePath) {
		try {
			logger.debug("Trying relative path [" + relativePath + "] against base location: " + location);

			UrlResource resource = (UrlResource) location.createRelative(relativePath);
			if (exists(resource)) {
				logger.debug("Found matching resource: " + resource);
				return resource;
			}
			else {
				logger.trace("Relative resource doesn't exist or isn't readable: " + resource);
			}
		} catch (IOException ex) {
			logger.debug("Failed to create relative resource - trying next resource location", ex);
		}

		return null;
	}

	public static byte[] getBytes(Resource resource) {
		try {
			return ByteStreams.toByteArray(resource.getInputStream());
		} catch (IOException e) {
			throw Throwables.propagate(e);
		}
	}

	public static String getPath(Resource resource) {
		try {
			String path = resource.getURL().getPath();
			logger.debug(String.format("Found path for resource: [%s] path :[%s]", resource, path));
			return path;
		} catch (IOException e) {
			throw Throwables.propagate(e);
		}
	}

	public static Resource getResource(byte[] bytes) {
		return new InMemoryResource(bytes);
	}

	public static Resource getResource(ByteArrayOutputStream os) {
		return getResource(os.toByteArray());
	}

	public static InputStream getInputStream(Resource resource) {
		return Exceptions.propagate(() -> {
			return resource.getInputStream();
		});
	}

	public static void getInputStream(Resource resource, ExceptionConsumer<InputStream> consumer) {
		VCloseables.with(getInputStream(resource), consumer);
	}

	public static boolean isValidResource(Resource resource) {
		if (resource.exists() && resource.isReadable()) {
			return true;
		}
		return false;
	}

	public static Resource getResource(String resourceLocation, ApplicationContext context) {
		return VIterables.getSingleResult(getResources(resourceLocation, context));
	}

	public static List<Resource> getResources(String resourceLocation, ApplicationContext context) {
		logger.trace("Resolving classpath resource at: [" + resourceLocation + "]");
		return Exceptions.propagate(() -> Lists.newArrayList(context.getResources(resourceLocation)));
	}

	public static List<Resource> getConcreteResources(Resource resource) {
		logger.printf(Level.DEBUG, "Retrieving concrete file resources for resource [%s]", resource);
		List<File> files = Lists.newArrayList();
		LinkedList<File> filesToProcess = Lists.newLinkedList();
		filesToProcess.add(new File(getPath(resource)));

		while (!filesToProcess.isEmpty()) {
			File file = filesToProcess.pop();
			if (!file.isDirectory()) {
				files.add(file);
			}
			else {
				filesToProcess.addAll(Arrays.asList(file.listFiles()));
			}
		}

		List<Resource> concreteFileResources = VLists.transform(
				getResourcePaths(VFiles.getPaths(files)), (path) -> getResource(path, Spring.getCurrentApplicationContext()));

		logger.debug("Retrieved concrete file resources " + concreteFileResources);
		return concreteFileResources;
	}

	public static Iterable<String> getResourcePaths(Iterable<String> paths) {
		List<String> classpathEntries = VClassPath.getClasspathEntries();
		return VIterables.transform(paths, (path) -> {
			for (String classpathEntry : classpathEntries) {
				if (path.startsWith(classpathEntry)) {
					return path.replace(classpathEntry, "");
				}
			}

			return path;
		});
	}
}