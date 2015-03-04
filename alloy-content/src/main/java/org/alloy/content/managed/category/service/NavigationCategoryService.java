package org.alloy.content.managed.category.service;

import java.util.Map;
import java.util.Optional;

import org.alloy.content.category.domain.NavigationCategory;
import org.alloy.content.category.domain.NavigationCategoryEntity;
import org.alloy.content.category.domain.NavigationCategoryFragment;
import org.alloy.metal.collections.list.AList;
import org.alloy.metal.collections.list.MutableList;
import org.alloy.metal.collections.list._Lists;
import org.alloy.metal.collections.map.AMultimap;
import org.alloy.metal.collections.map.MutableMap;
import org.alloy.metal.collections.map._Maps;
import org.alloy.metal.extensibility.ConfigurationGatherer;
import org.alloy.metal.object._Domain;
import org.alloy.persistence.managed.GenericDao;
import org.alloy.persistence.utilities._Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NavigationCategoryService extends ConfigurationGatherer<NavigationCategoryFragment> {
	@Autowired
	private GenericDao genericDao;

	public Optional<NavigationCategory> findByName(String name) {
		Optional<? extends NavigationCategoryFragment> parentFragment = this.findFragmentByName(name);
		if (!parentFragment.isPresent()) {
			return Optional.empty();
		}

		return Optional.of(this.resolve(parentFragment.get()));
	}

	public Optional<? extends NavigationCategoryFragment> findFragmentByName(String name) {
		Map<String, NavigationCategoryFragment> configuredCategoryMap = this.getConfiguredCategoryNameMap();
		if (configuredCategoryMap.containsKey(name)) {
			return Optional.of(configuredCategoryMap.get(name));
		}

		return genericDao.find(NavigationCategoryEntity.class, _Query.where("entity.name = :name").setParameter("name", name));
	}

	public void remove(NavigationCategoryEntity category) {
		genericDao.remove(category);
	}

	private NavigationCategory resolve(NavigationCategoryFragment fragment) {
		AList<NavigationCategoryFragment> allCategoryFragments = _Lists.list(fragment)
				.traverse((subFragment) -> this.findFragmentsByParentName(subFragment.getName()))
				.collectList();

		MutableMap<String, NavigationCategory> categoryMap = _Maps.map();

		for (NavigationCategoryFragment categoryFragment : allCategoryFragments) {
			NavigationCategory category = new NavigationCategory();
			_Domain.copy(category, categoryFragment);
			categoryMap.put(category.getName(), category);
		}

		for (NavigationCategoryFragment categoryFragment : allCategoryFragments) {
			NavigationCategory targetCategory = categoryMap.get(categoryFragment.getName());
			targetCategory.setParentCategory(categoryMap.get(categoryFragment.getParentCategoryName()));
		}

		AMultimap<String, NavigationCategory> parentNameMap =
				_Maps.multiMap((category) -> category.getParentCategory().getName(),
						categoryMap.values().filter((category) -> category.getParentCategory() != null));

		for (NavigationCategory navigationCategory : categoryMap.values()) {
			navigationCategory.getSubCategories().addAll(parentNameMap.get(navigationCategory.getName()).asCollection());
		}

		return categoryMap.get(fragment.getName());
	}

	private MutableList<NavigationCategoryFragment> findFragmentsByParentName(String name) {
		MutableList<NavigationCategoryFragment> fragments = _Lists.list();
		AMultimap<String, NavigationCategoryFragment> configuredCategoryParentNameMultimap = this.getConfiguredCategoryParentNameMultimap();
		fragments.addAll(configuredCategoryParentNameMultimap.get(name));
		fragments.addAll(genericDao.findAll(NavigationCategoryEntity.class,
				_Query.where("entity.parentCategoryName = :parentCategoryName")
						.setParameter("parentCategoryName", name)));
		return fragments;
	}

	private Map<String, NavigationCategoryFragment> getConfiguredCategoryNameMap() {
		return _Maps.map((item) -> item.getName(), _Lists.wrap(this.getResolvedItems())).asMap();
	}

	private AMultimap<String, NavigationCategoryFragment> getConfiguredCategoryParentNameMultimap() {
		return _Maps.multiMap((item) -> item.getParentCategoryName(), _Lists.wrap(this.getResolvedItems()));
	}
}