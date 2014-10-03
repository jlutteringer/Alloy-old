package org.alloy.content.content.domain;

public interface ContentRelation extends AlloyContent, CommonContentCriteria {
	public ContentType getTarget();
}