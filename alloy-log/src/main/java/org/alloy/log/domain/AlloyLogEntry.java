package org.alloy.log.domain;

import org.alloy.metal.object.DomainObject;
import org.alloy.metal.object.audit.TimeStamped;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface AlloyLogEntry extends DomainObject, TimeStamped {
	@JsonIgnore
	public AlloyLog getLog();

	public String getMessage();

	public void setMessage(String message);

	public void setLog(AlloyLog log);
}