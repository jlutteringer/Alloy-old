package org.alloy.site.request;

import org.springframework.web.context.request.ServletWebRequest;

public interface RequestProcessor {
	public void processRequest(ServletWebRequest request);
}
