package org.alloy.site.managed.resource.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.alloy.metal.domain.Path;
import org.alloy.metal.order.Orderable;
import org.alloy.site.resource.handler.AlloyPathTransformer;
import org.apache.logging.log4j.Level;
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

import com.google.common.collect.Lists;

//FUTURE find a way to get the font awesome font resources to work with different servlet context paths
//right now its hardcoded

//FUTURE make bundling optional - if bundling is turned off then the bundle resources tags should just
//expand to each individual component of the bundle
@Service("resourceHttpRequestHandler")
public class AlloyResourceHttpRequestHandler extends ResourceHttpRequestHandler {
	private static final Logger logger = LogManager.getLogger(AlloyResourceHttpRequestHandler.class);

	@Autowired
	private AlloyResourceResolverService resourceResolver;

	@Autowired
	protected List<AlloyPathTransformer> pathTransformers = Lists.newArrayList();

	@PostConstruct
	public void init() {
		Collections.sort(pathTransformers, Orderable.comparator());
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
			logger.debug("Determined media type '" + mediaType + "' for " + resource);
		}
		else {
			logger.debug("No media type found for " + resource + " - not sending a content-type header");
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
		Path path = Path.of(request.getServletPath());

		if (!StringUtils.hasText(path.getPath()) || isInvalidPath(path.getPath())) {
			if (logger.isDebugEnabled()) {
				logger.debug("Ignoring invalid resource path [" + path + "]");
			}
			return null;
		}

		for (AlloyPathTransformer transformer : pathTransformers) {
			logger.printf(Level.TRACE, "Applying transformer [%s] to path [%s]", transformer, path);
			if (transformer.canHandle(path)) {
				path = transformer.transform(path);
				logger.printf(Level.DEBUG, "Transformer transformed path to [%s]", path);
			}
			else {
				logger.trace("Transformer does not apply to path");
			}
		}

		return resourceResolver.getResource(path);
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

	@Override
	public void afterPropertiesSet() throws Exception {
		// No op
	}
}