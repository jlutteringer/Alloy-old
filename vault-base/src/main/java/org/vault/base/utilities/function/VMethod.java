package org.vault.base.utilities.function;

import java.util.List;

import com.google.common.collect.Lists;

public class VMethod<T> {
	private String name;
	private List<Object> argments = Lists.newArrayList();
	private Class<T> returnType;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Object> getArgments() {
		return argments;
	}

	public void setArgments(List<Object> argments) {
		this.argments = argments;
	}

	public Class<T> getReturnType() {
		return returnType;
	}

	public void setReturnType(Class<T> returnType) {
		this.returnType = returnType;
	}

	public static <T> VMethod<T> create(String name, Class<T> returnType, Object... arguments) {
		VMethod<T> method = new VMethod<>();
		method.setName(name);
		method.setReturnType(returnType);
		method.setArgments(Lists.newArrayList(arguments));
		return method;
	}
}
