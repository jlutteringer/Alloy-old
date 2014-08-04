package org.alloy.site.security.managed.filter;

import org.alloy.site.managed.filter.AlloyRequestProcessorFilter;
import org.alloy.site.managed.request.RequestLifecycle;
import org.springframework.stereotype.Component;

public class AlloyFilters {
	@Component("alloyPreSecurityFilter")
	public static class PreSecurityFilter extends AlloyRequestProcessorFilter {
		@Override
		protected RequestLifecycle getRequestLifecycle() {
			return RequestLifecycle.PRE_SECURITY;
		}
	}

	@Component("alloyPostSecurityFilter")
	public static class PostSecurityFilter extends AlloyRequestProcessorFilter {
		@Override
		protected RequestLifecycle getRequestLifecycle() {
			return RequestLifecycle.POST_SECURITY;
		}
	}
}