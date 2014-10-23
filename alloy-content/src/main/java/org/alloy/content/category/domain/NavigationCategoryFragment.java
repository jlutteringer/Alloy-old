package org.alloy.content.category.domain;

import org.alloy.metal.object.FriendlyNamed;

public interface NavigationCategoryFragment extends FriendlyNamed {
	public String getParentCategoryName();

	public void setParentCategoryName(String parentCategoryName);
}