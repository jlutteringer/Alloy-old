package org.alloy.metal.extensibility;

import java.util.List;

import org.alloy.metal.collections.iterable._Iterable;
import org.alloy.metal.collections.lists._List;
import org.alloy.metal.function._Function;
import org.alloy.metal.reflection._Class;
import org.alloy.metal.reflection._Reflection;

public class TypeFilteringConfigurationResolver<T, N> extends AbstractConfigurationResolver<T, N> {
	@Override
	protected List<N> resolveItems(List<T> configurations) {
		Class<?> filteringType = _Reflection.getTypeArguments(AbstractConfigurationResolver.class, this.getClass()).get(1);
		Iterable<T> filteredConfigurations = _Iterable.filter(configurations, _Class.include(_List.list(filteringType)));
		return _List.list(_Iterable.transform(filteredConfigurations, _Function.cast()));
	}
}