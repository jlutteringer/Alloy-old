package org.alloy.metal.spring;

import org.alloy.metal.collections.iterable._Iterable;
import org.alloy.metal.reflection._Reflection;

public abstract class TemplateAlloyBean<T> extends AlloyBean {
	@SuppressWarnings("unchecked")
	protected Class<T> runtimeType1 = (Class<T>) _Iterable.first(_Reflection.getTypeArguments(TemplateAlloyBean.class, this.getClass()));
}