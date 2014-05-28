package org.vault.base.delegator;

import org.vault.base.domain.Orderable;
import org.vault.base.utilities.function.VFunctional;
import org.vault.base.utilities.matcher.Matcher;

public interface Delegate<N> extends VFunctional, Matcher<N>, Orderable {

}
