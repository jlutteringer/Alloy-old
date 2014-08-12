package org.alloy.site.request;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.MessageSource;
import org.springframework.ui.context.Theme;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

public class RequestInformation {
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected WebRequest webRequest;
	protected Locale locale;
	protected TimeZone timeZone;
	protected Theme theme;
	protected Map<String, Object> additionalProperties = new HashMap<String, Object>();
	protected MessageSource messageSource;

	public HttpServletRequest getRequest() {
		return request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public WebRequest getWebRequest() {
		return webRequest;
	}

	public void setWebRequest(WebRequest webRequest) {
		this.webRequest = webRequest;
		if (webRequest instanceof ServletWebRequest) {
			this.request = ((ServletWebRequest) webRequest).getRequest();
			this.response = ((ServletWebRequest) webRequest).getResponse();
		}
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public TimeZone getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(TimeZone timeZone) {
		this.timeZone = timeZone;
	}

	public Theme getTheme() {
		return theme;
	}

	public void setTheme(Theme theme) {
		this.theme = theme;
	}

	public Map<String, Object> getAdditionalProperties() {
		return additionalProperties;
	}

	public void setAdditionalProperties(Map<String, Object> additionalProperties) {
		this.additionalProperties = additionalProperties;
	}

	public MessageSource getMessageSource() {
		return messageSource;
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	// TODO
	public String getRequestURIWithoutContext() {
		String requestURIWithoutContext = null;

		if (request.getRequestURI() != null) {
			if (request.getContextPath() != null) {
				requestURIWithoutContext = request.getRequestURI().substring(request.getContextPath().length());
			} else {
				requestURIWithoutContext = request.getRequestURI();
			}

			// Remove JSESSION-ID or other modifiers
			int pos = requestURIWithoutContext.indexOf(";");
			if (pos >= 0) {
				requestURIWithoutContext = requestURIWithoutContext.substring(0, pos);
			}
		}

		return requestURIWithoutContext;
	}

	// TODO
	public boolean isSecure() {
		boolean secure = false;
		if (request != null) {
			secure = ("HTTPS".equalsIgnoreCase(request.getScheme()) || request.isSecure());
		}
		return secure;
	}
}