package org.alloy.domain.embedded;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.alloy.metal.object.audit.AlloyTimeStamp;

@Embeddable
public class EmbeddedAlloyTimeStamp implements AlloyTimeStamp {
	private static final long serialVersionUID = -120427921767538835L;

	@Column(name = "createdDate")
	protected Instant createdDate = Instant.now();

	@Column(name = "modifiedDate")
	protected Instant modifiedDate = Instant.now();

	@Override
	public Instant getCreatedDate() {
		return createdDate;
	}

	@Override
	public void setCreatedDate(Instant createdDate) {
		this.createdDate = createdDate;
	}

	@Override
	public Instant getModifiedDate() {
		return modifiedDate;
	}

	@Override
	public void setModifiedDate(Instant modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
}