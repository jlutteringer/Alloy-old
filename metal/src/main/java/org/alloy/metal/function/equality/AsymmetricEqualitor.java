package org.alloy.metal.function.equality;

import java.util.function.BiFunction;

@FunctionalInterface
public interface AsymmetricEqualitor<T, N> extends BiFunction<T, N, Boolean> {

}
