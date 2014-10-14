package org.alloy.content.category.domain;

import java.io.Serializable;
import java.util.List;

import org.alloy.metal.collections.lists._List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class NavigationCategory extends AbstractNavigationCategory implements Serializable {
	private static final long serialVersionUID = -7791359243957218898L;

	private NavigationCategory parentCategory;
	private List<NavigationCategory> subCategories = _List.list();

	@JsonIgnore
	public NavigationCategory getParentCategory() {
		return parentCategory;
	}

	public void setParentCategory(NavigationCategory parentCategory) {
		this.parentCategory = parentCategory;
	}

	// TODO
	public String getUrl() {
		return null;
	}

	public List<NavigationCategory> getSubCategories() {
		return subCategories;
	}
}