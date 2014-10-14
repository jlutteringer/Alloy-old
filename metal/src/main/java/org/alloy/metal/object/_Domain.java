package org.alloy.metal.object;

import java.util.Map;

import org.alloy.metal.collections.map._Map;

public class _Domain {
	public static <T extends Named> Map<String, T> mapNames(Iterable<T> iterable) {
		return _Map.map((element) -> element.getName(), iterable);
	}

	public static <T extends FriendlyNamed> Map<String, T> mapFriendlyNames(Iterable<T> iterable) {
		return _Map.map((element) -> element.getFriendlyName(), iterable);
	}

	public static void copy(Named target, Named source) {
		target.setName(source.getName());
	}

	public static void copy(FriendlyNamed target, FriendlyNamed source) {
		copy((Named) target, (Named) source);
		target.setFriendlyName(source.getFriendlyName());
	}
}