package org.alloy.site.managed.location;

import java.util.TimeZone;

import org.springframework.web.context.request.WebRequest;

//TODO
public interface TimeZoneResolver {
	public TimeZone resolveTimeZone(WebRequest request);
}
