package org.vault.base.tag;

import java.util.Optional;

public class Tag {
	private String name;
	private Optional<Object> value;

	public Tag(String name) {
		this(name, null);
	}

	public Tag(String name, Object value) {
		this.name = name;
		this.value = Optional.ofNullable(value);
	}

	public String getName() {
		return name;
	}

	public Optional<Object> getValue() {
		return value;
	}
}
