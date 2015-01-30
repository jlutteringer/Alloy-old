package org.alloy.content.category.domain;

import java.io.Serializable;
import java.util.List;

import org.alloy.metal.collections.iterable._Iterable;
import org.alloy.metal.collections.lists._Lists;
import org.alloy.metal.url._Url;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class NavigationCategory extends AbstractNavigationCategory implements Serializable {
	private static final long serialVersionUID = -7791359243957218898L;

	private NavigationCategory parentCategory;
	private List<NavigationCategory> subCategories = _Lists.list();

	@JsonIgnore
	public NavigationCategory getParentCategory() {
		return parentCategory;
	}

	public void setParentCategory(NavigationCategory parentCategory) {
		this.parentCategory = parentCategory;
	}

	public String getUrl() {
		Iterable<String> urlPathStrings = _Iterable.transform(_Iterable.traverse(_Lists.list(this), (category) -> {
			if (category.getParentCategory() != null) {
				return _Lists.list(category.getParentCategory());
			}
			return _Lists.list();
		}), (category) -> category.getName());

		return _Url.create().addPath(urlPathStrings).build().getUrl();
	}

	public List<NavigationCategory> getSubCategories() {
		return subCategories;
	}
}