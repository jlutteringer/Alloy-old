package org.vault.base.domain;

import java.io.Serializable;
import java.util.Date;

public interface AuditableContainer extends Serializable {
	public Date getDateCreated();

	public void setDateCreated(Date dateCreated);

	public Long getCreatedBy();

	public void setCreatedBy(Long createdBy);

	public Date getDateUpdated();

	public void setDateUpdated(Date dateUpdated);

	public Long getUpdatedBy();

	public void setUpdatedBy(Long updatedBy);
}
