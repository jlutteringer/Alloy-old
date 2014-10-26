package org.alloy.metal.object.audit;

import java.io.Serializable;
import java.time.Instant;

public interface AlloyTimeStamp extends Serializable {
	public Instant getCreatedDate();

	public void setCreatedDate(Instant createdDate);

	public Instant getModifiedDate();

	public void setModifiedDate(Instant modifiedDate);
}