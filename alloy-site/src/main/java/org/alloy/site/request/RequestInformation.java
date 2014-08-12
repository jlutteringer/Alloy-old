package org.alloy.site.request;

import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.MessageSource;
import org.springframework.ui.context.Theme;
import org.springframework.web.context.request.WebRequest;

public interface RequestInformation {
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