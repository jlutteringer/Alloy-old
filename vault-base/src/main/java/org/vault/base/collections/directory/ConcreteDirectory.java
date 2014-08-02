package org.vault.base.collections.directory;

import java.util.List;

import org.vault.base.collections.iterable._Iterable;
import org.vault.base.collections.lists._Lists;
import org.vault.base.utilities.function.VFunctions;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

public class ConcreteDirectory<T, N> implements Directory<T, N> {
	private List<DirectoryIndex<T, N>> indexes = Lists.newArrayList();

	@Override
	public boolean containsKey(T key) {
		if (!this.get(key).isEmpty()) {
			return true;
		}
		return false;
	}

	@Override
	public boolean containsValue(N value) {
		return Iterables.contains(_Directory.unwrapValues(indexes), value);
	}

	@Override
	public List<N> get(T key) {
		Iterable<DirectoryIndex<T, N>> matchingValues =
				_Directory.<T, N> getDirectoryIndexKeyMatcher(key).getMatches(indexes);

		return _Lists.list(_Directory.unwrapValues(matchingValues));
	}

	@Override
	public List<N> get() {
		return _Lists.list(_Directory.unwrapValues(indexes));
	}

	@Override
	public List<DirectoryEntry<T, N>> getEntries(T key) {
		Iterable<DirectoryIndex<T, N>> matchingValues =
				_Directory.<T, N> getDirectoryIndexKeyMatcher(key).getMatches(indexes);

		return _Lists.list(_Directory.unwrapEntries(matchingValues));
	}

	@Override
	public List<DirectoryEntry<T, N>> getEntries() {
		return _Lists.list(_Directory.unwrapEntries(indexes));
	}

	@Override
	public List<DirectoryIndex<T, N>> getIndexes(T key) {
		Iterable<DirectoryIndex<T, N>> matchingValues =
				_Directory.<T, N> getDirectoryIndexKeyMatcher(key).getMatches(indexes);

		return _Lists.list(matchingValues);
	}

	@Override
	public List<DirectoryIndex<T, N>> getIndexes() {
		return _Lists.list(indexes);
	}

	@Override
	public DirectoryEntry<T, N> put(T key, N value) {
		return this.put(key, value, false);
	}

	@Override
	public DirectoryEntry<T, N> put(T key, N value, boolean createNew) {
		DirectoryIndex<T, N> index = null;
		if (!createNew) {
			index = _Iterable.first(_Directory.<T, N> getDirectoryIndexKeyMatcher(key).getMatches(indexes));
		}

		if (index == null) {
			index = _Directory.createIndex(key);
			indexes.add(index);
		}

		return index.add(value);
	}

	@Override
	public boolean remove(T key) {
		boolean itemsRemoved = false;

		for (DirectoryIndex<T, N> index : this.getIndexes(key)) {
			indexes.remove(index);
			itemsRemoved = true;
		}
		return itemsRemoved;
	}

	@Override
	public void putAll(Directory<T, N> directory) {
		for (DirectoryEntry<T, N> entry : directory.getEntries()) {
			this.put(entry.getKey(), entry.getValue());
		}
	}

	@Override
	public void clear() {
		indexes.clear();
	}

	@Override
	public long size() {
		return VFunctions.accumulate(indexes, (index) -> index.size());
	}

	@Override
	public boolean isEmpty() {
		return indexes.isEmpty();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Directory: [");
		for (DirectoryIndex<T, N> index : this.getIndexes()) {
			sb.append(index);
			sb.append(", ");
		}
		sb.delete(sb.length() - ", ".length(), sb.length());
		sb.append("]");
		return sb.toString();
	}

	public static class ConcreteDirectoryIndex<T, N> implements DirectoryIndex<T, N> {
		private T key;
		private List<DirectoryEntry<T, N>> entries = Lists.newArrayList();

		@Override
		public T getKey() {
			return key;
		}

		@Override
		public List<DirectoryEntry<T, N>> getEntries() {
			return entries;
		}

		@Override
		public long size() {
			return entries.size();
		}

		@Override
		public DirectoryEntry<T, N> add(N value) {
			DirectoryEntry<T, N> entry = _Directory.createEntry(value, this);
			entries.add(entry);
			return entry;
		}

		@Override
		public void setKey(T key) {
			this.key = key;
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append("Directory Index: {key = ");
			sb.append(key);
			sb.append(", entries = [");
			for (DirectoryEntry<T, N> entry : this.getEntries()) {
				sb.append(entry.getValue());
				sb.append(", ");
			}
			sb.delete(sb.length() - ", ".length(), sb.length());
			sb.append("}");
			return sb.toString();
		}
	}

	public static class ConcreteDirectoryEntry<T, N> implements DirectoryEntry<T, N> {
		private N value;
		private DirectoryIndex<T, N> index;

		@Override
		public N getValue() {
			return value;
		}

		@Override
		public T getKey() {
			return index.getKey();
		}

		@Override
		public void setValue(N value) {
			this.value = value;
		}

		@Override
		public void setIndex(DirectoryIndex<T, N> index) {
			this.index = index;
		}
	}
}
