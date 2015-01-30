package org.alloy.content.managed.category.service;

import java.util.List;
import java.util.Map;

import org.alloy.content.category.domain.NavigationCategory;
import org.alloy.content.category.domain.NavigationCategoryEntity;
import org.alloy.content.category.domain.NavigationCategoryFragment;
import org.alloy.metal.collections.iterable._Iterable;
import org.alloy.metal.collections.lists._Lists;
import org.alloy.metal.collections.map._Map;
import org.alloy.metal.extensibility.ConfigurationGatherer;
import org.alloy.metal.object._Domain;
import org.alloy.persistence.managed.GenericDao;
import org.alloy.persistence.utilities._Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Multimap;

@Service
public class NavigationCategoryService extends ConfigurationGatherer<NavigationCategoryFragment> {
	@Autowired
	private GenericDao genericDao;

	public NavigationCategory findByName(String name) {
		NavigationCategoryFragment parentFragment = this.findFragmentByName(name);
		if (parentFragment == null) {
			return null;
		}

		return this.resolve(parentFragment);
	}

	public NavigationCategoryFragment findFragmentByName(String name) {
		Map<String, NavigationCategoryFragment> configuredCategoryMap = this.getConfiguredCategoryNameMap();
		if (configuredCategoryMap.containsKey(name)) {
			return configuredCategoryMap.get(name);
		}

		return genericDao.find(NavigationCategoryEntity.class, _Query.where("entity.name = :name").setParameter("name", name));
	}

	public void remove(NavigationCategoryEntity category) {
		genericDao.remove(category);
	}

	private NavigationCategory resolve(NavigationCategoryFragment fragment) {
		Iterable<NavigationCategoryFragment> allCategoryFragments =
				_Iterable.traverse(_Lists.list(fragment), (subFragment) -> this.findFragmentsByParentName(subFragment.getName()));

		Map<String, NavigationCategory> categoryMap = _Map.map();

		for (NavigationCategoryFragment categoryFragment : allCategoryFragments) {
			NavigationCategory category = new NavigationCategory();
			_Domain.copy(category, categoryFragment);
			categoryMap.put(category.getName(), category);
		}

		for (NavigationCategoryFragment categoryFragment : allCategoryFragments) {
			NavigationCategory targetCategory = categoryMap.get(categoryFragment.getName());
			targetCategory.setParentCategory(categoryMap.get(categoryFragment.getParentCategoryName()));
		}

		Multimap<String, NavigationCategory> parentNameMap =
				_Map.multiMap((category) -> category.getParentCategory().getName(),
						_Iterable.filter(categoryMap.values(), (category) -> category.getParentCategory() != null));

		for (NavigationCategory navigationCategory : categoryMap.values()) {
			navigationCategory.getSubCategories().addAll(parentNameMap.get(navigationCategory.getName()));
		}

		return categoryMap.get(fragment.getName());
	}

	private List<NavigationCategoryFragment> findFragmentsByParentName(String name) {
		List<NavigationCategoryFragment> fragments = _Lists.list();
		Multimap<String, NavigationCategoryFragment> configuredCategoryParentNameMultimap = this.getConfiguredCategoryParentNameMultimap();
		fragments.addAll(configuredCategoryParentNameMultimap.get(name));
		fragments.addAll(genericDao.findAll(NavigationCategoryEntity.class,
				_Query.where("entity.parentCategoryName = :parentCategoryName")
						.setParameter("parentCategoryName", name)));
		return fragments;
	}

	private Map<String, NavigationCategoryFragment> getConfiguredCategoryNameMap() {
		return _Map.map((item) -> item.getName(), this.getResolvedItems());
	}

	private Multimap<String, NavigationCategoryFragment> getConfiguredCategoryParentNameMultimap() {
		return _Map.multiMap((item) -> item.getParentCategoryName(), this.getResolvedItems());
	}
}