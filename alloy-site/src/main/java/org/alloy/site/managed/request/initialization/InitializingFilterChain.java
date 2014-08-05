package org.alloy.site.managed.request.initialization;

import javax.servlet.http.HttpServletRequest;

import org.alloy.metal.utilities._Exception;
import org.alloy.site.filter.HaltFilterChainException;
import org.alloy.site.filter.ManagedAlloyFilterChain;
import org.alloy.site.managed.request.LocaleResolver;
import org.alloy.site.managed.request.RequestManager;
import org.alloy.site.managed.request.TimeZoneResolver;
import org.alloy.site.request.RequestContext;
import org.alloy.site.request.RequestProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

@Component
public class InitializingFilterChain extends ManagedAlloyFilterChain {
	@Autowired
	private void init(RequestContextInitializer requestContextInitializer) {
		filters.add(requestContextInitializer);
	}

	@Component
	public static class RequestContextInitializer extends RequestProcessor {
		@Autowired
		protected LocaleResolver localeResolver;

		@Autowired
		protected TimeZoneResolver timeZoneResolver;

		@Autowired
		protected RequestManager requestManager;

		@Autowired
		protected MessageSource messageSource;

		@Override
		protected void processRequest(ServletWebRequest request) {
			RequestContext context = this.createRequestContext();
			context.setWebRequest(request);

			// When a user elects to switch his sandbox, we want to invalidate the current session. We'll then redirect the
			// user to the current URL so that the configured filters trigger again appropriately.
			Boolean reprocessRequest = (Boolean) request.getAttribute(RequestContext.REPROCESS_PARAM_NAME, WebRequest.SCOPE_REQUEST);
			if (reprocessRequest != null && reprocessRequest) {
				logger.debug("Reprocessing request");
				HttpServletRequest hsr = request.getRequest();

				requestManager.clearSessionAttributes(request);

				// TODO
				StringBuffer url = hsr.getRequestURL();
				if (hsr.getQueryString() != null) {
					url.append('?').append(hsr.getQueryString());
				}

				_Exception.propagate(() -> request.getResponse().sendRedirect(url.toString()));

				throw new HaltFilterChainException("Reprocess required, redirecting user");
			}

			context.setLocale(localeResolver.resolveLocale(request));
			context.setMessageSource(messageSource);
			context.setTimeZone(timeZoneResolver.resolveTimeZone(request));

			RequestContext.setRequestContext(context);
		}

		private RequestContext createRequestContext() {
			// TODO Auto-generated method stub
			return null;
		}

		protected void clearBroadleafSessionAttrs(WebRequest request) {

		}
	}
}