package org.vault.base.spring.beans;

import org.vault.base.collections.iterable.VIterables;
import org.vault.base.reflection.VReflection;

public abstract class VaultBeanT1<T> extends VaultBean {
	@SuppressWarnings("unchecked")
	protected Class<T> runtimeType1 = (Class<T>) VIterables.first(VReflection.getTypeArguments(VaultBeanT1.class, this.getClass()));
}