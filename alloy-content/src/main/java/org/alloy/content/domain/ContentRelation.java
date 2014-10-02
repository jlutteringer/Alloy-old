package org.alloy.content.domain;

public interface ContentRelation extends AlloyContent, CommonContentCriteria {
	public ContentType getTarget();
}