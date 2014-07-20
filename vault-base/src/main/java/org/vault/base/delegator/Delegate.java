package org.vault.base.delegator;

import java.util.function.Predicate;

import org.vault.base.domain.Orderable;

public interface Delegate<N> extends Predicate<N>, Orderable {

}
