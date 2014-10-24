package org.alloy.domain.embedded;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.alloy.metal.object.audit.AlloyTimeStamp;

@Embeddable
public class EmbeddedAlloyTimeStamp implements AlloyTimeStamp {
	private static final long serialVersionUID = -120427921767538835L;

	@Column(name = "createdDate")
	protected LocalDateTime createdDate;

	@Column(name = "modifiedDate")
	protected LocalDateTime modifiedDate;

	@Override
	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	@Override
	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	@Override
	public LocalDateTime getModifiedDate() {
		return modifiedDate;
	}

	@Override
	public void setModifiedDate(LocalDateTime modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
}