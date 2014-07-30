package org.vault.site.managed.resource.service.bundle;

import java.util.Collections;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.vault.base.ant.Ant;
import org.vault.base.collections.iterable.VIterables;
import org.vault.base.collections.lists.VLists;
import org.vault.base.configuration.AbstractConfigurationResolver;
import org.vault.base.delegator.AbstractDelegator;
import org.vault.base.delegator.ClassTypeDelegate;
import org.vault.core.managed.resource.VaultResourceManager;
import org.vault.site.resource.bundle.Bundle;
import org.vault.site.resource.bundle.configuration.AntPathConfigurationComponent;
import org.vault.site.resource.bundle.configuration.BundleConfiguration;
import org.vault.site.resource.bundle.configuration.BundleConfigurationComponent;
import org.vault.site.resource.bundle.configuration.NameConfigurationComponent;

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
		List<Bundle> bundles = VLists.list();
		configurations.forEach((configuration) -> {
			Bundle bundle = new Bundle(configuration.getName());
			configuration.getComponents().forEach((component) -> {
				bundle.getBundleResourceLocations().addAll(delegator.getDelegate(component).resolve(component));
			});
			bundles.add(bundle);
		});

		return bundles;
	}

	@Component
	static class BundleConfigurationResolverDelegator extends AbstractDelegator<BundleConfigurationDelegate<?>, BundleConfigurationComponent> {

	}

	abstract static class BundleConfigurationDelegate<T extends BundleConfigurationComponent> extends ClassTypeDelegate<T, BundleConfigurationComponent> {
		protected final Logger logger = LogManager.getLogger(this.getClass());

		@SuppressWarnings("unchecked")
		public List<String> resolve(BundleConfigurationComponent configuration) {
			return this.resolveInternal((T) configuration);
		}

		protected abstract List<String> resolveInternal(T configuration);
	}

	@Component
	static class NameConfigurationDelegate extends BundleConfigurationDelegate<NameConfigurationComponent> {
		@Override
		protected List<String> resolveInternal(NameConfigurationComponent configuration) {
			return Collections.singletonList(configuration.getName());
		}
	}

	@Component
	static class AntPathConfigurationDelegate extends BundleConfigurationDelegate<AntPathConfigurationComponent> {
		@Autowired
		protected VaultResourceManager resourceManager;

		@Override
		protected List<String> resolveInternal(AntPathConfigurationComponent configuration) {
			Iterable<String> concreteResourcePaths = resourceManager.getConcreteVisibleResourcePaths("resources");
			logger.debug("For concrete resource paths: " + concreteResourcePaths);

			List<String> matchingPaths = VLists.list(VIterables.filter(concreteResourcePaths, Ant.pathMatcher(configuration.getPattern())));
			logger.debug("Found matching paths: " + matchingPaths);
			return matchingPaths;
		}
	}
}