package org.vault.site.security.managed.filter;

import org.springframework.stereotype.Component;
import org.vault.site.managed.filter.VaultRequestProcessorFilter;
import org.vault.site.managed.request.RequestLifecycle;

public class VaultFilters {
	@Component("vaultPreSecurityFilter")
	public static class PreSecurityFilter extends VaultRequestProcessorFilter {
		@Override
		protected RequestLifecycle getRequestLifecycle() {
			return RequestLifecycle.PRE_SECURITY;
		}
	}

	@Component("vaultPostSecurityFilter")
	public static class PostSecurityFilter extends VaultRequestProcessorFilter {
		@Override
		protected RequestLifecycle getRequestLifecycle() {
			return RequestLifecycle.POST_SECURITY;
		}
	}
}