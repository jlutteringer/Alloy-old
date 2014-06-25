package org.vault.site.managed.resource.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;
import org.vault.core.managed.resource.VaultClasspathResourceManager;
import org.vault.site.resource.handler.VaultResourceHandler;
import org.vault.site.resource.handler.VaultResourceTransformer;

import com.google.common.collect.Lists;

@Service("resourceHttpRequestHandler")
public class VaultResourceHttpRequestHandler extends ResourceHttpRequestHandler {
	private static final Logger logger = LogManager.getLogger(VaultResourceHttpRequestHandler.class);

	@Autowired
	protected List<VaultResourceHandler> handlers = Lists.newArrayList();

	@Autowired
	protected List<VaultResourceTransformer> transformers = Lists.newArrayList();

	@Autowired
	private VaultClasspathResourceManager resourceManager;

	@PostConstruct
	public void init() {
		Collections.sort(handlers, (o1, o2) -> Integer.compare(o1.getOrder(), o2.getOrder()));
	}

	@Override
	public void handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		checkAndPrepare(request, response, true);

		// check whether a matching resource exists
		Resource resource = getResource(request);
		if (resource == null) {
			logger.debug("No matching resource found - returning 404");
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		MediaType mediaType = null;
		try {
			// check the resource's media type
			mediaType = getMediaType(resource);
		} catch (Exception e) {
			// Eat it
		}

		if (mediaType != null) {
			if (logger.isDebugEnabled()) {
				logger.debug("Determined media type '" + mediaType + "' for " + resource);
			}
		}
		else {
			if (logger.isDebugEnabled()) {
				logger.debug("No media type found for " + resource + " - not sending a content-type header");
			}
		}

		// header phase
		try {
			if (new ServletWebRequest(request, response).checkNotModified(resource.lastModified())) {
				logger.debug("Resource not modified - returning 304");
				return;
			}
		} catch (FileNotFoundException e) {
			// Eat it
		}
		setHeaders(response, resource, mediaType);

		// content phase
		if (METHOD_HEAD.equals(request.getMethod())) {
			logger.trace("HEAD request - skipping content");
			return;
		}
		writeContent(response, resource);
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

		for (VaultResourceHandler handler : handlers) {
			if (handler.canHandle(path)) {
				Resource resource = handler.getResource(path, getLocations());
				for (VaultResourceTransformer transformer : transformers) {
					if (transformer.canHandle(path, resource)) {
						resource = transformer.transform(path, resource);
					}
				}
				return resource;
			}
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	public List<Resource> getLocations() {
		return (List<Resource>) (List<?>) resourceManager.getLocations("resources");
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