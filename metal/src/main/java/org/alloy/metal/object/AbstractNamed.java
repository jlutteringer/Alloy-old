package org.alloy.metal.object;

public class AbstractNamed implements Named {
	protected String name;

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}
}