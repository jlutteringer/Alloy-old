package org.alloy.content.category.domain;

public class NavigationCategoryFragment extends AbstractNavigationCategory {
	private String parentCategoryKey;

	public String getParentCategoryKey() {
		return parentCategoryKey;
	}

	public void setParentCategoryKey(String parentCategoryKey) {
		this.parentCategoryKey = parentCategoryKey;
	}
}