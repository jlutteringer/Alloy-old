package org.alloy.content.content.domain;

import java.util.List;

public interface ContentType extends AlloyContent {
	public List<ContentField> getFields();

	public List<ContentRelation> getRelations();
}