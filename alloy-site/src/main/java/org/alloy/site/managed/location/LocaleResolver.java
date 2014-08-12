package org.alloy.site.managed.location;

import java.util.Locale;

import org.springframework.web.context.request.WebRequest;

public interface LocaleResolver {
	public Locale resolveLocale(WebRequest request);
}