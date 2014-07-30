package org.vault.base.utilities.function;

import java.util.function.BiFunction;

@FunctionalInterface
public interface Equalitor<T> extends BiFunction<T, T, Boolean> {

}