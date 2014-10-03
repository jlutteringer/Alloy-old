package org.alloy.content.content.domain;

import javax.persistence.MappedSuperclass;

import org.alloy.domain.entity.DescribedEntity;

@MappedSuperclass
public abstract class AlloyContentEntity extends DescribedEntity implements AlloyContent {
	private static final long serialVersionUID = 3911325210961026238L;
}