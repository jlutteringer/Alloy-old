package org.alloy.metal.collections.directory;

import java.util.List;

public interface Directory<T, N> {
	public boolean containsKey(T key);

	public boolean containsValue(N value);

	public List<N> get(T key);

	public List<N> get();

	public List<DirectoryEntry<T, N>> getEntries(T key);

	public List<DirectoryEntry<T, N>> getEntries();

	public List<DirectoryIndex<T, N>> getIndexes(T key);

	public List<DirectoryIndex<T, N>> getIndexes();

	public DirectoryEntry<T, N> put(T key, N value);

	public DirectoryEntry<T, N> put(T key, N value, boolean createNew);

	public boolean remove(T key);

	public void putAll(Directory<T, N> directory);

	public void clear();

	public long size();

	public boolean isEmpty();
}