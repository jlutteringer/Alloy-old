package org.vault.site.managed.request;

import org.springframework.web.context.request.ServletWebRequest;
import org.vault.base.domain.Orderable;

public interface RequestProcessor extends Orderable {
	public RequestLifecycle getRequestLifecycle();

	public void process(ServletWebRequest servletWebRequest);
}