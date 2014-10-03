package org.alloy.content.managed.category.service;

import org.alloy.content.category.domain.NavigationCategory;
import org.alloy.content.category.domain.NavigationCategoryEntity;
import org.alloy.content.managed.category.dao.CategoryDao;
import org.alloy.persistence.service.AbstractDaoWrapper;
import org.springframework.stereotype.Service;

@Service
public class CategoryService extends AbstractDaoWrapper<NavigationCategoryEntity, CategoryDao> {
	public NavigationCategory findByKey(String key) {
		// TODO Auto-generated method stub
		return null;
	}

}