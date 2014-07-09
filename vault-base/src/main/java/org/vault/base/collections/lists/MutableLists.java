package org.vault.base.collections.lists;

import java.util.Collections;
import java.util.List;

import org.vault.base.domain.Orderable;

public class MutableLists {
	public static <T extends Orderable> void sort(List<T> list) {
		Collections.sort(list, Orderable.comparator());
	}
}