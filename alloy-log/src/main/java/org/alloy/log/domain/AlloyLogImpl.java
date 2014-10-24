package org.alloy.log.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TableGenerator;

import org.alloy.domain.entity.NamedTimeStampedEntity;

@Entity
public class AlloyLogImpl extends NamedTimeStampedEntity implements AlloyLog {
	private static final long serialVersionUID = -6861171877437917714L;
	private static final String ENTITY_NAME = "alloyLog";

	@Id
	@GeneratedValue(generator = ENTITY_NAME + "Id", strategy = GenerationType.TABLE)
	@TableGenerator(name = ENTITY_NAME + "Id", table = "sequenceGenerator", pkColumnName = "idName", valueColumnName = "idVal", pkColumnValue = ENTITY_NAME, allocationSize = 50)
	@Column(name = ENTITY_NAME + "Id")
	protected Long id;

	@Column(name = "isOpen")
	protected boolean isOpen = true;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void close() {
		this.isOpen = false;
	}

	@Override
	public boolean isOpen() {
		return isOpen;
	}
}