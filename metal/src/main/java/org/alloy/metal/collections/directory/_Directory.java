package org.alloy.metal.collections.directory;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import org.alloy.metal.collections.directory.ConcreteDirectory.ConcreteDirectoryEntry;
import org.alloy.metal.collections.directory.ConcreteDirectory.ConcreteDirectoryIndex;
import org.alloy.metal.collections.iterable._Iterable;
import org.alloy.metal.function.Tuple.Pair;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;

public class _Directory {
	public static <N, T> Iterable<N> unwrapValues(Iterable<DirectoryIndex<T, N>> indexes) {
		return _Iterable.transform(_Directory.unwrapEntries(indexes), new Function<DirectoryEntry<T, N>, N>() {
			@Override
			public N apply(DirectoryEntry<T, N> first) {
				return first.getValue();
			}
		});
	}

	public static <T, N> Iterable<DirectoryEntry<T, N>> unwrapEntries(Iterable<DirectoryIndex<T, N>> indexes) {
		return _Iterable.multiplexingIterable(indexes, new Function<DirectoryIndex<T, N>, Iterator<DirectoryEntry<T, N>>>() {
			@Override
			public Iterator<DirectoryEntry<T, N>> apply(DirectoryIndex<T, N> first) {
				return first.getEntries().iterator();
			}
		});
	}

	public static <T, N> Predicate<DirectoryIndex<T, N>> getDirectoryIndexKeyMatcher(final T key) {
		return (index) -> Objects.equal(key, index.getKey());
	}

	public static <T, N> DirectoryIndex<T, N> createIndex(T key) {
		DirectoryIndex<T, N> index = new ConcreteDirectoryIndex<T, N>();
		index.setKey(key);
		return index;
	}

	public static <T, N> DirectoryEntry<T, N> createEntry(N value, DirectoryIndex<T, N> index) {
		DirectoryEntry<T, N> entry = new ConcreteDirectoryEntry<T, N>();
		entry.setValue(value);
		entry.setIndex(index);
		return entry;
	}

	public static <T, N> Directory<T, N> newDirectory() {
		Directory<T, N> directory = new ConcreteDirectory<T, N>();
		return directory;
	}

	public static <T, N> Directory<T, N> newUnkeyedDirectory(List<N> values) {
		Directory<T, N> directory = _Directory.newDirectory();
		for (N value : values) {
			directory.put(null, value);
		}
		return directory;
	}

	@SafeVarargs
	public static <T, N> Directory<T, N> newUnkeyedDirectory(N... values) {
		return _Directory.newUnkeyedDirectory(Lists.newArrayList(values));
	}

	public static <T, N> Directory<T, N> newKeyedDirectory(List<Pair<T, N>> pairs) {
		Directory<T, N> directory = _Directory.newDirectory();
		for (Pair<T, N> pair : pairs) {
			directory.put(pair.getFirst(), pair.getSecond());
		}
		return directory;
	}
}
