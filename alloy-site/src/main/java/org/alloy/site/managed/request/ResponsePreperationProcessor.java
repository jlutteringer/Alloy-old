package org.alloy.site.managed.request;

import org.alloy.metal.order.Phase;
import org.alloy.site.request.AnyRequestMatcher;
import org.alloy.site.request.RequestLifecycle;
import org.alloy.site.request.StandaloneRequestProcessor;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

@Component
public class ResponsePreperationProcessor extends StandaloneRequestProcessor {
	@Override
	public void processRequest(ServletWebRequest request) {
		// FUTURE this is for font awesome and should be only for eot|ttf|otf|woff
		// there should be a way to more easily do this, however... similar to apache!
		request.getResponse().setHeader("Access-Control-Allow-Origin", "*");
	}

	@Override
	public RequestLifecycle getRequestLifecycle() {
		return RequestLifecycle.PRE_SECURITY;
	}

	@Override
	public Phase getLifecyclePhase() {
		return Phase.PRE_INITIALIZATION;
	}

	@Override
	public RequestMatcher getRequestMatcher() {
		return new AnyRequestMatcher();
	}
}