package org.alloy.site.managed.request.initialization;

import org.alloy.site.filter.ManagedAlloyFilterChain;
import org.alloy.site.managed.location.LocaleResolver;
import org.alloy.site.managed.location.TimeZoneResolver;
import org.alloy.site.managed.request.RequestManager;
import org.alloy.site.managed.request.TimeZoneResolver;
import org.alloy.site.request.DefaultRequestInformation;
import org.alloy.site.request.EmbeddedRequestProcessor;
import org.alloy.site.request.RequestContext;
import org.alloy.site.request.RequestInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

public class InitializingFilterChain extends ManagedAlloyFilterChain {
	@Autowired
	private void init(RequestContextInitializer requestContextInitializer) {
		filters.add(requestContextInitializer);
	}

	public static class RequestContextInitializer extends EmbeddedRequestProcessor {
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
			return new DefaultRequestInformation();
		}
	}
}
