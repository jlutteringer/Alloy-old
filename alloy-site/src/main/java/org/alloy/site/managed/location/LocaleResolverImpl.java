package org.alloy.site.managed.location;

import java.util.Locale;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

//TODO
@Component
public class LocaleResolverImpl implements LocaleResolver {
//	private final Log LOG = LogFactory.getLog(BroadleafLocaleResolverImpl.class);
//
//	/**
//	* Parameter/Attribute name for the current language
//	*/
//	public static String LOCALE_VAR = "blLocale";
//
//	/**
//	* Parameter/Attribute name for the current language
//	*/
//	public static String LOCALE_CODE_PARAM = "blLocaleCode";
//
//	/**
//	* Attribute indicating that the LOCALE was pulled from session. Other filters may want to
//	* behave differently if this is the case.
//	*/
//	public static String LOCALE_PULLED_FROM_SESSION = "blLocalePulledFromSession";
//
//	@Resource(name = "blLocaleService")
//	private LocaleService localeService;

	@Override
	public Locale resolveLocale(WebRequest request) {
//		Locale locale = null;
//
//		// First check for request attribute
//		locale = (Locale) request.getAttribute(LOCALE_VAR, WebRequest.SCOPE_REQUEST);
//
//		// Second, check for a request parameter
//		if (locale == null && BLCRequestUtils.getURLorHeaderParameter(request, LOCALE_CODE_PARAM) != null) {
//			String localeCode = BLCRequestUtils.getURLorHeaderParameter(request, LOCALE_CODE_PARAM);
//			locale = localeService.findLocaleByCode(localeCode);
//			if (BLCRequestUtils.isOKtoUseSession(request)) {
//				request.removeAttribute(BroadleafCurrencyResolverImpl.CURRENCY_VAR, WebRequest.SCOPE_GLOBAL_SESSION);
//			}
//			if (LOG.isTraceEnabled()) {
//				LOG.trace("Attempt to find locale by param " + localeCode + " resulted in " + locale);
//			}
//		}
//
//		// Third, check the session
//		if (locale == null && BLCRequestUtils.isOKtoUseSession(request)) {
//			locale = (Locale) request.getAttribute(LOCALE_VAR, WebRequest.SCOPE_GLOBAL_SESSION);
//			if (LOG.isTraceEnabled()) {
//				LOG.trace("Attempt to find locale from session resulted in " + locale);
//			}
//
//			if (locale != null) {
//				request.setAttribute(LOCALE_PULLED_FROM_SESSION, Boolean.TRUE, WebRequest.SCOPE_REQUEST);
//			}
//
//		}
//
//		// Finally, use the default
//		if (locale == null) {
//			locale = localeService.findDefaultLocale();
//			if (BLCRequestUtils.isOKtoUseSession(request)) {
//				request.removeAttribute(BroadleafCurrencyResolverImpl.CURRENCY_VAR, WebRequest.SCOPE_GLOBAL_SESSION);
//			}
//			if (LOG.isTraceEnabled()) {
//				LOG.trace("Locale set to default locale " + locale);
//			}
//		}
//
//		// Set the default locale to override Spring's cookie resolver
//		request.setAttribute(LOCALE_VAR, locale, WebRequest.SCOPE_REQUEST);
//		java.util.Locale javaLocale = BroadleafRequestContext.convertLocaleToJavaLocale(locale);
//		request.setAttribute(CookieLocaleResolver.LOCALE_REQUEST_ATTRIBUTE_NAME, javaLocale, WebRequest.SCOPE_REQUEST);
//
//		if (BLCRequestUtils.isOKtoUseSession(request)) {
//			request.setAttribute(LOCALE_VAR, locale, WebRequest.SCOPE_GLOBAL_SESSION);
//		}
//		return locale;
		return null;
	}
}
