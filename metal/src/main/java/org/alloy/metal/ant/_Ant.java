package org.alloy.metal.ant;

import java.util.function.Predicate;

public class _Ant {
	public static Predicate<String> pathMatcher(String pattern) {
		return new AntPathPredicate(pattern);
	}
}