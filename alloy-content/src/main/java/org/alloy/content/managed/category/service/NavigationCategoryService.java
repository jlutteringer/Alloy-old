package org.alloy.content.managed.category.service;

import java.util.List;
import java.util.Map;

import org.alloy.content.category.domain.NavigationCategory;
import org.alloy.content.category.domain.NavigationCategoryFragment;
import org.alloy.metal.collections.lists._List;
import org.alloy.metal.collections.map._Map;
import org.alloy.metal.extensibility.AbstractConfigurationResolver;
import org.alloy.metal.object._Domain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Multimap;

@Service
public class NavigationCategoryService extends AbstractConfigurationResolver<NavigationCategoryFragment, NavigationCategory> {
	@Autowired
	private NavigationCategoryEntityService navigationCategoryEntityService;

	public NavigationCategory findByKey(String key) {
		return null;
	}

	@Override
	protected List<NavigationCategory> resolveItems(List<NavigationCategoryFragment> configurations) {
		Map<String, NavigationCategory> categoryMap = _Map.map();

		for (NavigationCategoryFragment configuration : configurations) {
			NavigationCategory category = new NavigationCategory();
			_Domain.copy(category, configuration);
			categoryMap.put(category.getName(), category);
		}

		for (NavigationCategoryFragment configuration : configurations) {
			NavigationCategory targetCategory = categoryMap.get(configuration.getName());
			targetCategory.setParentCategory(categoryMap.get(configuration.getParentCategoryKey()));
		}

		Multimap<String, NavigationCategory> parentNameMap = _Map.multiMap((category) -> category.getParentCategory().getName(), categoryMap.values());
		for (NavigationCategory navigationCategory : categoryMap.values()) {
			navigationCategory.getSubCategories().addAll(parentNameMap.get(navigationCategory.getName()));
		}

		return _List.list(categoryMap.values());
	}

//	private NavigationCategory getBaseCategory(String key, Map<String, NavigationCategory> categoryMap) {
//		if (categoryMap.containsKey(key)) {
//			return categoryMap.get(key);
//		}
//		else {
//			return navigationCategoryEntityService.find(_Query.where("entity.key = :key").setParameter("key", key));
//		}
//	}
//
	private Map<String, NavigationCategory> getCategoryMap() {
		Map<String, NavigationCategory> configuredCategoryMap = this.getConfiguredCategoryMap();
		return null;
	}

	private Map<String, NavigationCategory> getConfiguredCategoryMap() {
		return _Map.map((item) -> item.getName(), this.getResolvedItems());
	}
}