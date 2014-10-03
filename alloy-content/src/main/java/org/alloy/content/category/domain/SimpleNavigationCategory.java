package org.alloy.content.category.domain;

import java.util.List;

import org.alloy.metal.collections.lists._List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class SimpleNavigationCategory implements NavigationCategory {
	private static final long serialVersionUID = -7791359243957218898L;

	private String friendlyName;
	private String key;
	private List<NavigationCategory> parentCategories = _List.list();
	private List<NavigationCategory> subCategories = _List.list();

	@Override
	public String getFriendlyName() {
		return friendlyName;
	}

	@Override
	public String getKey() {
		return key;
	}

	public void setFriendlyName(String friendlyName) {
		this.friendlyName = friendlyName;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@Override
	@JsonIgnore
	public List<NavigationCategory> getParentCategories() {
		return parentCategories;
	}

	@Override
	public List<NavigationCategory> getSubCategories() {
		return subCategories;
	}
}