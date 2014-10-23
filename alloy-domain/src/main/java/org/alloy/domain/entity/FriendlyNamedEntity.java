package org.alloy.domain.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.alloy.metal.object.FriendlyNamed;

@MappedSuperclass
public abstract class FriendlyNamedEntity extends NamedEntity implements FriendlyNamed {
	private static final long serialVersionUID = 3312236560485232833L;

	@Column(name = "friendlyName", nullable = false)
	private String friendlyName;

	@Override
	public String getFriendlyName() {
		return friendlyName;
	}

	@Override
	public void setFriendlyName(String friendlyName) {
		this.friendlyName = friendlyName;
	}
}