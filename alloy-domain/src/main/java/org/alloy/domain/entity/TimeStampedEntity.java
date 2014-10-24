package org.alloy.domain.entity;

import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;

import org.alloy.domain.embedded.EmbeddedAlloyTimeStamp;
import org.alloy.metal.object.audit.AlloyTimeStamp;
import org.alloy.metal.object.audit.TimeStamped;

@MappedSuperclass
public abstract class TimeStampedEntity extends BaseEntity implements TimeStamped {
	private static final long serialVersionUID = -1982805352144300557L;

	@Embedded
	protected EmbeddedAlloyTimeStamp timeStamp = new EmbeddedAlloyTimeStamp();

	@Override
	public AlloyTimeStamp getTimestamp() {
		return timeStamp;
	}
}