package org.alloy.content.category.domain;

import java.io.Serializable;
import java.util.List;

public interface NavigationCategory extends Serializable {
	public String getFriendlyName();

	public String getKey();

	public List<NavigationCategory> getParentCategories();

	public List<NavigationCategory> getSubCategories();
}