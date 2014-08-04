package org.alloy.site.managed.request;

import org.springframework.web.context.request.ServletWebRequest;

public interface RequestProcessor extends RequestLifecycleOrdeable {
	public void process(ServletWebRequest servletWebRequest);
}