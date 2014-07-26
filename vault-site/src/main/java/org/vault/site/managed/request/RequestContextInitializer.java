package org.vault.site.managed.request;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;
import org.vault.base.domain.phase.Phase;

@Component
public class RequestContextInitializer extends AbstractRequestProcessor {
	@Override
	public void process(ServletWebRequest servletWebRequest) {
		logger.debug("Initializing request");
	}

	@Override
	public RequestLifecycle getRequestLifecycle() {
		return RequestLifecycle.PRE_SECURITY;
	}

	@Override
	public Phase getLifecyclePhase() {
		return Phase.INITIALIZATION;
	}
}