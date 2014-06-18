package org.vault.base.utilities.function;

import org.vault.base.utilities.Types;

import com.google.common.base.Throwables;

public class VMethods {
	@SuppressWarnings("unchecked")
	public static <T> T invoke(VMethod<T> method, Object object) {
		try {
			Object value = object.getClass()
					.getMethod(method.getName(), Types.getTypes(method.getArgments()).toArray(new Class<?>[0]))
					.invoke(object, method.getArgments().toArray());

			return (T) value;
		} catch (Exception e) {
			throw Throwables.propagate(e);
		}
	}
}
