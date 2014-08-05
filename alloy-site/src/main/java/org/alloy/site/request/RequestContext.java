package org.alloy.site.request;

import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.alloy.metal.state.ThreadLocalManager;
import org.springframework.context.MessageSource;
import org.springframework.ui.context.Theme;
import org.springframework.web.context.request.WebRequest;

public interface RequestContext {
	public static String REPROCESS_PARAM_NAME = "REPROCESS_REQUEST";
	public static String SITE_ENFORCE_PRODUCTION_WORKFLOW_KEY = "site.enforce.production.workflow.update";

	class RequestContextThreadlocal {
		protected static final ThreadLocal<RequestContext> REQUEST_CONTEXT = ThreadLocalManager.createThreadLocal(RequestContext.class);
	}

	public static RequestContext getRequestContext() {
		return RequestContextThreadlocal.REQUEST_CONTEXT.get();
	}

	public static void setRequestContext(RequestContext requestContext) {
		RequestContextThreadlocal.REQUEST_CONTEXT.set(requestContext);
	}

	public HttpServletRequest getRequest();

	public HttpServletResponse getResponse();

	public void setWebRequest(WebRequest webRequest);

	public WebRequest getWebRequest();

	public Locale getLocale();

	public void setLocale(Locale locale);

	public Theme getTheme();

	public void setTheme(Theme theme);

	public Map<String, Object> getAdditionalProperties();

	public void setAdditionalProperties(Map<String, Object> additionalProperties);

	public MessageSource getMessageSource();

	public void setMessageSource(MessageSource messageSource);

	public TimeZone getTimeZone();

	public void setTimeZone(TimeZone timeZone);

	public String getRequestURIWithoutContext();

	public boolean isSecure();
}