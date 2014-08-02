package org.alloy.metal.collections.lists;

import java.util.Collections;
import java.util.List;

import org.alloy.metal.order.Orderable;

public class MutableLists {
	public static <T extends Orderable> void sort(List<T> list) {
		Collections.sort(list, Orderable.comparator());
	}
}