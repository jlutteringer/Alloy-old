package org.alloy.site.request;

import org.alloy.metal.state.ThreadLocalManager;
import org.springframework.web.context.request.WebRequest;

public class RequestContext {
	public static String REPROCESS_PARAM_NAME = "REPROCESS_REQUEST";
	protected static final ThreadLocal<RequestInformation> REQUEST_CONTEXT = ThreadLocalManager.createThreadLocal(RequestInformation.class);

	public static RequestInformation getRequestInformation() {
		return REQUEST_CONTEXT.get();
	}

	public static WebRequest getRequest() {
		return getRequestInformation().getWebRequest();
	}

	public static void setRequestInformation(RequestInformation requestContext) {
		REQUEST_CONTEXT.set(requestContext);
	}
}
