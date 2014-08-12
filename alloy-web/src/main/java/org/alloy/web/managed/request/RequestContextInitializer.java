package org.alloy.web.managed.request;

import org.alloy.metal.order.Phase;
import org.alloy.site.managed.location.LocaleResolver;
import org.alloy.site.managed.location.TimeZoneResolver;
import org.alloy.site.managed.request.RequestManager;
import org.alloy.site.request.RequestContext;
import org.alloy.site.request.RequestInformation;
import org.alloy.site.request.StandaloneRequestProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

@Component
public class RequestContextInitializer extends StandaloneRequestProcessor {
	@Autowired
	protected LocaleResolver localeResolver;

	@Autowired
	protected TimeZoneResolver timeZoneResolver;

	@Autowired
	protected RequestManager requestManager;

	@Autowired
	protected MessageSource messageSource;

	@Override
	public void processRequest(ServletWebRequest request) {
		RequestInformation context = this.createRequestInformation();
		context.setWebRequest(request);
		context.setLocale(localeResolver.resolveLocale(request));
		context.setMessageSource(messageSource);
		context.setTimeZone(timeZoneResolver.resolveTimeZone(request));

		RequestContext.setRequestInformation(context);
	}

	private RequestInformation createRequestInformation() {
		return new RequestInformation();
	}

	@Override
	public Phase getLifecyclePhase() {
		return Phase.INITIALIZATION;
	}
}