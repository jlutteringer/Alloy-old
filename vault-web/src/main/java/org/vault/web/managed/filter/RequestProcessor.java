package org.vault.web.managed.filter;

import org.springframework.web.context.request.ServletWebRequest;
import org.vault.base.domain.Orderable;
import org.vault.web.request.RequestLifecycle;

public interface RequestProcessor extends Orderable {
	public RequestLifecycle getRequestLifecycle();

	public void process(ServletWebRequest servletWebRequest);
}