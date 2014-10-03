package org.alloy.content.content.domain;

import java.util.List;

public interface ContentCategory extends AlloyContent {
	public ContentCategory getParentCategory();

	public List<ContentCategory> getSubCategories();
}