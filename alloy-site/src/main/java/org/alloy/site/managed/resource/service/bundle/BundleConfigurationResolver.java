package org.alloy.site.managed.resource.service.bundle;

import java.util.Collections;
import java.util.List;

import org.alloy.core.managed.resource.AlloyResourceManager;
import org.alloy.metal.ant._Ant;
import org.alloy.metal.collections.iterable._Iterable;
import org.alloy.metal.collections.lists._List;
import org.alloy.metal.extensibility.AbstractConfigurationResolver;
import org.alloy.metal.spring.delegator.AbstractDelegator;
import org.alloy.metal.spring.delegator.ClassTypeDelegate;
import org.alloy.site.resource.bundle.Bundle;
import org.alloy.site.resource.bundle.configuration.AntPathConfigurationComponent;
import org.alloy.site.resource.bundle.configuration.ResourceConfiguration;
import org.alloy.site.resource.bundle.configuration.ResourceConfigurationComponents;
import org.alloy.site.resource.bundle.configuration.NameConfigurationComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BundleConfigurationResolver extends AbstractConfigurationResolver<ResourceConfiguration, Bundle> {
	@Autowired
	private BundleConfigurationResolverDelegator delegator;

	@Autowired
	private void init(ResourceBundlingService bundler) {
		this.getResolvedItems().forEach(bundler::registerBundle);
	}

	@Override
	protected List<Bundle> resolveItems(List<ResourceConfiguration> configurations) {
		List<Bundle> bundles = _List.list();
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
	static class BundleConfigurationResolverDelegator extends AbstractDelegator<BundleConfigurationDelegate<?>, ResourceConfigurationComponents> {

	}

	abstract static class BundleConfigurationDelegate<T extends ResourceConfigurationComponents> extends ClassTypeDelegate<T, ResourceConfigurationComponents> {
		protected final Logger logger = LogManager.getLogger(this.getClass());

		@SuppressWarnings("unchecked")
		public List<String> resolve(ResourceConfigurationComponents configuration) {
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
		protected AlloyResourceManager resourceManager;

		@Override
		protected List<String> resolveInternal(AntPathConfigurationComponent configuration) {
			Iterable<String> concreteResourcePaths = resourceManager.getConcreteVisibleResourcePaths("resources");
			logger.debug("For concrete resource paths: " + concreteResourcePaths);

			List<String> matchingPaths = _List.list(_Iterable.filter(concreteResourcePaths, _Ant.pathMatcher(configuration.getPattern())));
			logger.debug("Found matching paths: " + matchingPaths);
			return matchingPaths;
		}
	}
}