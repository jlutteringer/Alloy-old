package org.vault.base.resource;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.util.InMemoryResource;
import org.vault.base.closeable.VCloseables;
import org.vault.base.utilities.exception.Exceptions;
import org.vault.base.utilities.function.ExceptionConsumer;

import com.google.common.base.Throwables;
import com.google.common.io.ByteStreams;

public class VResources {
	private static final Logger logger = LogManager.getLogger(VResources.class);

	public static Resource findExistingRelative(Resource location, String relativePath) {
		try {
			logger.debug("Trying relative path [" + relativePath + "] against base location: " + location);

			UrlResource resource = (UrlResource) location.createRelative(relativePath);
			if (resource.exists() && resource.isReadable()) {
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
}
