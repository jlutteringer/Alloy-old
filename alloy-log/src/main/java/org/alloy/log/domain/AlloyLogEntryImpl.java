package org.alloy.log.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.alloy.domain.entity.TimeStampedEntity;

@Entity
@Table(name = "alloyLogEntry", indexes = { @Index(name = "alloyLogIndex", columnList = "alloyLogId") })
public class AlloyLogEntryImpl extends TimeStampedEntity implements AlloyLogEntry {
	private static final long serialVersionUID = -8369903888232690555L;
	private static final String ENTITY_NAME = "alloyLogEntry";

	@Id
	@GeneratedValue(generator = ENTITY_NAME + "Id", strategy = GenerationType.TABLE)
	@TableGenerator(name = ENTITY_NAME + "Id", table = "sequenceGenerator", pkColumnName = "idName", valueColumnName = "idVal", pkColumnValue = ENTITY_NAME, allocationSize = 50)
	@Column(name = ENTITY_NAME + "Id")
	protected Long id;

	@ManyToOne(targetEntity = AlloyLogImpl.class, optional = false)
	@JoinColumn(name = "alloyLogId")
	protected AlloyLog log;

	@Column(name = "message")
	@Lob
	private String message;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public AlloyLog getLog() {
		return log;
	}

	@Override
	public void setLog(AlloyLog log) {
		this.log = log;
	}
}