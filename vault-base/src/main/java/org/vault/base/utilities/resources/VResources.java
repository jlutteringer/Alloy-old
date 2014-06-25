package org.vault.base.utilities.resources;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.util.InMemoryResource;

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
		if (resource instanceof UrlResource) {
			try {
				return ((UrlResource) resource).getURL().getPath();
			} catch (IOException e) {
				throw Throwables.propagate(e);
			}
		}

		throw new RuntimeException("Can't get path from resource " + resource);
	}

	public static Resource getResource(byte[] bytes) {
		return new InMemoryResource(bytes);
	}

	public static Resource getResource(ByteArrayOutputStream os) {
		return getResource(os.toByteArray());
	}
}
