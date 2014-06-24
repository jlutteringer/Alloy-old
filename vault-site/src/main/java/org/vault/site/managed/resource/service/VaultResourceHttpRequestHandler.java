package org.vault.site.managed.resource.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;
import org.vault.base.utilities.url.Urls;
import org.vault.core.managed.resource.VaultClasspathResourceManager;
import org.vault.site.resource.handler.VaultResourceHandler;

import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@Service("resourceHttpRequestHandler")
public class VaultResourceHttpRequestHandler extends ResourceHttpRequestHandler {
	private static final Logger logger = LogManager.getLogger(VaultResourceHttpRequestHandler.class);

	@Autowired
	protected List<VaultResourceHandler> handlers = Lists.newArrayList();

	@Autowired
	private VaultClasspathResourceManager resourceManager;

	@Value("${project.version}")
	private String version;

	private Map<String, String> resolvedResources = Maps.newConcurrentMap();

	@PostConstruct
	public void init() {
		Collections.sort(handlers, (o1, o2) -> Integer.compare(o1.getOrder(), o2.getOrder()));
	}

	/**
	 * Checks to see if the requested path corresponds to a registered bundle. If so, returns the generated bundle.
	 * Otherwise, checks to see if any of the configured GeneratedResourceHandlers can handle the given request.
	 * If neither of those cases match, delegates to the normal ResourceHttpRequestHandler
	 */
	@Override
	protected Resource getResource(HttpServletRequest request) {
		String path = request.getServletPath();

		if (!StringUtils.hasText(path) || isInvalidPath(path)) {
			if (logger.isDebugEnabled()) {
				logger.debug("Ignoring invalid resource path [" + path + "]");
			}
			return null;
		}

		if (resolvedResources.containsKey(path)) {
			try {
				return new UrlResource(resolvedResources.get(path));
			} catch (MalformedURLException e) {
				throw Throwables.propagate(e);
			}
		}

		for (VaultResourceHandler handler : handlers) {
			if (handler.canHandle(path)) {
				return handler.getResource(path, getLocations());
			}
		}

		return this.findDefaultResource(path);
	}

	private Resource findDefaultResource(String path) {
		String unversionedPath = Urls.unVersion(path, version);

		for (Resource location : this.getLocations()) {
			try {
				if (logger.isDebugEnabled()) {
					logger.debug("Trying relative path [" + unversionedPath + "] against base location: " + location);
				}
				Resource resource = location.createRelative("/resources" + unversionedPath);
				if (resource.exists() && resource.isReadable()) {
					if (logger.isDebugEnabled()) {
						logger.debug("Found matching resource: " + resource);
					}

					if (resource instanceof UrlResource) {
						resolvedResources.put(path, ((UrlResource) resource).getURL().toString());
					}
					return resource;
				}
				else if (logger.isTraceEnabled()) {
					logger.trace("Relative resource doesn't exist or isn't readable: " + resource);
				}
			} catch (IOException ex) {
				logger.debug("Failed to create relative resource - trying next resource location", ex);
			}
		}
		return null;
	}

	public List<Resource> getLocations() {
		return resourceManager.getLocations("resources");
	}

	protected String getContextName(HttpServletRequest request) {
		String contextName = request.getServerName();
		int pos = contextName.indexOf('.');
		if (pos >= 0) {
			contextName = contextName.substring(0, contextName.indexOf('.'));
		}
		return contextName;
	}

	// **NOTE** This method is lifted from HttpSessionSecurityContextRepository
	protected SecurityContext readSecurityContextFromSession(HttpSession httpSession) {
		if (httpSession == null) {
			return null;
		}

		Object ctxFromSession = httpSession.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
		if (ctxFromSession == null) {
			return null;
		}

		if (!(ctxFromSession instanceof SecurityContext)) {
			return null;
		}

		return (SecurityContext) ctxFromSession;
	}

	/* *********** */
	/* BOILERPLATE */
	/* *********** */

	public List<VaultResourceHandler> getHandlers() {
		return handlers;
	}

	public void setHandlers(List<VaultResourceHandler> handlers) {
		this.handlers = handlers;
	}
}