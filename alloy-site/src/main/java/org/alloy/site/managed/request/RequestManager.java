package org.alloy.site.managed.request;

import org.springframework.web.context.request.ServletWebRequest;

//TODO
public interface RequestManager {

	void clearSessionAttributes(ServletWebRequest request);

}
