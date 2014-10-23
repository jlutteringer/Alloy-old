package org.alloy.admin.managed.resources;

import java.util.List;

import org.alloy.content.category.domain.NavigationCategoryFragment;
import org.alloy.content.category.domain.SimpleNavigationCategoryFragment;
import org.alloy.metal.extensibility.AbstractConfigurationProvider;
import org.alloy.site.resource.ResourceConfigurationProvider;
import org.alloy.site.resource.configuration.ResourceConfiguration;
import org.alloy.site.resource.configuration.ResourceConfigurations;
import org.springframework.stereotype.Component;

@Component
public class AdminResourceConfiguration extends ResourceConfigurationProvider {
	@Override
	protected void addConfigurations(List<ResourceConfiguration> configurations) {
		configurations.add(ResourceConfigurations.alias("/css/admin-global-bundle.css")
				.include("/css/global-bundle.css")
				.include("/css/libraries/bootstrap-darkly.css")
				.build());

		configurations.add(ResourceConfigurations.alias("/js/admin-global-bundle.js")
				.include("/js/global-bundle.js")
				.include("/js/angular-bundle.js")
				.build());
	}

	@Component
	public static class AdminCategoryConfiguration extends AbstractConfigurationProvider<NavigationCategoryFragment> {
		@Override
		protected void addConfigurations(List<NavigationCategoryFragment> configurations) {
			NavigationCategoryFragment fragment = new SimpleNavigationCategoryFragment();
			fragment.setFriendlyName("Admin");
			fragment.setName("admin");

			NavigationCategoryFragment fragment1 = new SimpleNavigationCategoryFragment();
			fragment1.setFriendlyName("Subcategory 1");
			fragment1.setName("sub1");
			fragment1.setParentCategoryName("admin");

			configurations.add(fragment);
			configurations.add(fragment1);
		}
	}
}