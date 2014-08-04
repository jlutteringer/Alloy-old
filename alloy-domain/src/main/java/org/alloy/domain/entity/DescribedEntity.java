package org.alloy.domain.entity;

import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;

import org.alloy.domain.embedded.EmbeddedDescription;
import org.alloy.metal.object.Describeable;

@MappedSuperclass
public abstract class DescribedEntity extends BaseEntity implements Describeable {
	private static final long serialVersionUID = 1146824447894178159L;

	@Embedded
	protected EmbeddedDescription description = new EmbeddedDescription();

	@Override
	public String getName() {
		return description.getName();
	}

	@Override
	public void setName(String name) {
		description.setName(name);
	}

	@Override
	public String getDescription() {
		return description.getDescription();
	}

	@Override
	public void setDescription(String description) {
		this.description.setDescription(description);
	}
}