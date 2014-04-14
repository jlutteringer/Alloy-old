package org.vault.base.utilities.matcher;

import java.util.List;

import com.google.common.collect.Lists;

public abstract class AbstractMatcher<T> implements Matcher<T> {

	@Override
	public abstract boolean matches(T input);

	@Override
	public List<T> getMatches(List<T> inputs) {
		List<T> matches = Lists.newArrayList();
		for (T input : inputs) {
			if (matches(input)) {
				matches.add(input);
			}
		}
		return matches;
	}
}