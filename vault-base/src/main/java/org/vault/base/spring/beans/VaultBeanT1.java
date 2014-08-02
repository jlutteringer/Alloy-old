package org.vault.base.spring.beans;

import org.vault.base.collections.iterable._Iterable;
import org.vault.base.reflection.VReflection;

public abstract class VaultBeanT1<T> extends VaultBean {
	@SuppressWarnings("unchecked")
	protected Class<T> runtimeType1 = (Class<T>) _Iterable.first(VReflection.getTypeArguments(VaultBeanT1.class, this.getClass()));
}