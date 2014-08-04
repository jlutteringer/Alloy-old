package org.alloy.domain.embedded;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.alloy.metal.object.AuditableContainer;

@Embeddable
public class EmbeddedAuditable implements AuditableContainer {
	private static final long serialVersionUID = -3231805708321329236L;

	@Column(name = "DATE_CREATED", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;

	@Column(name = "CREATED_BY", updatable = false)
	private Long createdBy;

	@Column(name = "DATE_UPDATED")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateUpdated;

	@Column(name = "UPDATED_BY")
	private Long updatedBy;

	@Override
	public Date getDateCreated() {
		return dateCreated;
	}

	@Override
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	@Override
	public Long getCreatedBy() {
		return createdBy;
	}

	@Override
	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	@Override
	public Date getDateUpdated() {
		return dateUpdated;
	}

	@Override
	public void setDateUpdated(Date dateUpdated) {
		this.dateUpdated = dateUpdated;
	}

	@Override
	public Long getUpdatedBy() {
		return updatedBy;
	}

	@Override
	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}
}