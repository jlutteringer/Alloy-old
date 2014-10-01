package org.alloy.site.managed.request;

import org.springframework.web.context.request.ServletWebRequest;

//FUTURE
public interface RequestManager {

	void clearSessionAttributes(ServletWebRequest request);

}
