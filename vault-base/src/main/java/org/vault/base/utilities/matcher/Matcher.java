package org.vault.base.utilities.matcher;

import java.util.List;

public interface Matcher<T> {
	public boolean matches(T input);

	public List<T> getMatches(List<T> input);
}