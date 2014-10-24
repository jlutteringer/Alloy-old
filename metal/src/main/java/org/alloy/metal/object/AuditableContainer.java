package org.alloy.metal.object;

import java.io.Serializable;
import java.time.LocalDateTime;

public interface AuditableContainer extends Serializable {
	public LocalDateTime getDateCreated();

	public void setDateCreated(LocalDateTime dateCreated);

	public LocalDateTime getDateUpdated();

	public void setDateUpdated(LocalDateTime dateUpdated);
}
