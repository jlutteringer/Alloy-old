package org.alloy.content.category.domain;

public class SimpleNavigationCategoryFragment extends AbstractNavigationCategory implements NavigationCategoryFragment {
	private String parentCategoryName;

	@Override
	public String getParentCategoryName() {
		return parentCategoryName;
	}

	@Override
	public void setParentCategoryName(String parentCategoryKey) {
		this.parentCategoryName = parentCategoryKey;
	}
}