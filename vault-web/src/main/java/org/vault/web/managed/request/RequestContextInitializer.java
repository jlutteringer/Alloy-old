package org.vault.web.managed.request;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;
import org.vault.base.domain.phase.Phase;
import org.vault.web.managed.filter.AbstractRequestProcessor;
import org.vault.web.request.RequestLifecycle;

@Component
public class RequestContextInitializer extends AbstractRequestProcessor {
	@Override
	public void process(ServletWebRequest servletWebRequest) {
		logger.debug("Initializing request");
	}

	@Override
	public Phase getPhase() {
		return Phase.INITIALIZATION;
	}

	@Override
	public RequestLifecycle getRequestLifecycle() {
		return RequestLifecycle.PRE_SECURITY;
	}
}