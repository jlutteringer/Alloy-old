package org.alloy.site.managed.resource.service;

import java.io.ByteArrayOutputStream;

import org.alloy.metal.resource._Resource;
import org.alloy.metal.spring.AlloyBean;
import org.alloy.metal.utilities._Closeable;
import org.alloy.metal.utilities._Exception;
import org.alloy.site.resource.GeneratedResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

@Service
public class ResourceBundlingService extends AlloyBean {
	public Resource bundle(String name, Iterable<Resource> resources) {
		logger.debug("Creating bundle: {} with resources: {}", name, resources);
		return _Exception.propagate(() -> {
			byte[] result = _Closeable.withResult(new ByteArrayOutputStream(), (baos) -> {
				for (Resource resource : resources) {
					_Resource.getInputStream(resource, (inputStream) -> {
						StreamUtils.copy(inputStream, baos);
					});

					baos.write(System.getProperty("line.separator").getBytes());
				}

				return baos.toByteArray();
			});

			if (logger.isTraceEnabled()) {
				logger.trace("Created bundle:");
				logger.trace(new String(result));
			}
			return new GeneratedResource(result, name);
		});
	}
}