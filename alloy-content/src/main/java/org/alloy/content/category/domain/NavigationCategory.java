package org.alloy.content.category.domain;

import java.io.Serializable;
import java.util.List;

import org.alloy.metal.collections.list._Lists;
import org.alloy.metal.flow.Source;
import org.alloy.metal.url._Url;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class NavigationCategory extends AbstractNavigationCategory implements Serializable {
	private static final long serialVersionUID = -7791359243957218898L;

	private NavigationCategory parentCategory;
	private List<NavigationCategory> subCategories = _Lists.utilList();

	@JsonIgnore
	public NavigationCategory getParentCategory() {
		return parentCategory;
	}

	public void setParentCategory(NavigationCategory parentCategory) {
		this.parentCategory = parentCategory;
	}

	public String getUrl() {
		Source<String> urlPathStrings = _Lists.list(this)
				.traverse((category) -> {
					if (category.getParentCategory() != null) {
						return _Lists.list(category.getParentCategory());
					}
					return _Lists.list();
				})
				.map((category) -> category.getName());

		return _Url.create().addPath(urlPathStrings.cursor().toIterator()).build().getUrl();
	}

	public List<NavigationCategory> getSubCategories() {
		return subCategories;
	}
}