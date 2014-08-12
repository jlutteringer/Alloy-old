package org.alloy.web.managed.user;

import org.alloy.metal.order.Phase;
import org.alloy.site.request.RequestContext;
import org.alloy.site.request.RequestLifecycle;
import org.alloy.site.request.StandaloneRequestProcessor;
import org.alloy.web.user.UserContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

@Component
public class UserRequestProcessor extends StandaloneRequestProcessor {
	public static final String USER_CONTEXT_KEY = "user";

	@Override
	public void processRequest(ServletWebRequest request) {

	}

	public static UserContext getContext() {
		// TODO
		WebRequest request = RequestContext.getRequest();
//		CustomerContainer container = (CustomerContainer) request.getAttribute(PB_CUSTOMER_CONTAINER_SESSION_ATTR_NAME, WebRequest.SCOPE_SESSION);
//		if (container == null) {
//			container = new CustomerContainer();
//			request.setAttribute(PB_CUSTOMER_CONTAINER_SESSION_ATTR_NAME, container, WebRequest.SCOPE_SESSION);
//		}
//		return container;
		return null;
	}

	protected String getContextKey() {
		return USER_CONTEXT_KEY;
	}

	@Override
	public RequestLifecycle getRequestLifecycle() {
		return RequestLifecycle.POST_SECURITY;
	}

	@Override
	public Phase getLifecyclePhase() {
		return Phase.INITIALIZATION;
	}
}