package org.alloy.site.managed.request.initialization;

import org.alloy.metal.order.Phase;
import org.alloy.site.filter.ManagedAlloyFilterChain;
import org.alloy.site.managed.request.initialization.PreSecurityInitializingFilterChain.RequestContextInitializer;
import org.alloy.site.request.RequestProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

@Component
public class PostSecurityInitializingFilterChain extends ManagedAlloyFilterChain {
	public PostSecurityInitializingFilterChain() {
		this.lifecyclePhase = Phase.INITIALIZATION;
	}

	@Autowired
	private void init(RequestContextInitializer requestContextInitializer) {
		filters.add(requestContextInitializer);
	}

	@Component
	public static class UserInitializer extends RequestProcessor {
		@Override
		protected void processRequest(ServletWebRequest request) {
			// TODO Auto-generated method stub

		}
	}
}
