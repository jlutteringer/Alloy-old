package org.alloy.metal.object.audit;

import java.io.Serializable;
import java.time.LocalDateTime;

public interface AlloyTimeStamp extends Serializable {
	public LocalDateTime getCreatedDate();

	public void setCreatedDate(LocalDateTime createdDate);

	public LocalDateTime getModifiedDate();

	public void setModifiedDate(LocalDateTime modifiedDate);
}