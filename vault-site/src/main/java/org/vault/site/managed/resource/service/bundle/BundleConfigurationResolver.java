package org.vault.site.managed.resource.service.bundle;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.vault.base.configuration.AbstractConfigurationResolver;
import org.vault.base.delegator.AbstractDelegator;
import org.vault.base.delegator.ClassTypeDelegate;
import org.vault.site.resource.bundle.Bundle;
import org.vault.site.resource.bundle.BundleConfiguration;

import com.google.common.collect.Lists;

@Component
public class BundleConfigurationResolver extends AbstractConfigurationResolver<BundleConfiguration, Bundle> {
	@Autowired
	private BundleConfigurationResolverDelegator delegator;

	@Autowired
	private void init(ResourceBundlingService bundler) {
		this.getResolvedItems().forEach(bundler::registerBundle);
	}

	@Override
	protected List<Bundle> resolveItems(List<BundleConfiguration> configurations) {
		List<Bundle> bundles = Lists.newArrayList();
		configurations.forEach((configuration) -> bundles.add(delegator.getDelegate(configuration).resolve(configuration)));
		return bundles;
	}

	@Component
	static class BundleConfigurationResolverDelegator extends AbstractDelegator<BundleConfigurationDelegate<?>, BundleConfiguration> {

	}

	abstract static class BundleConfigurationDelegate<T> extends ClassTypeDelegate<T, BundleConfiguration> {
		public abstract Bundle resolve(BundleConfiguration configuration);
	}
}