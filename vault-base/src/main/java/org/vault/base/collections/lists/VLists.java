package org.vault.base.collections.lists;

import java.util.List;

import com.google.common.collect.Lists;

public class VLists {
	@SafeVarargs
	public static <T> List<T> create(T... item) {
		return Lists.newArrayList(item);
	}
}
