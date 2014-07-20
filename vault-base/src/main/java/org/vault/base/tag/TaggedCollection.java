package org.vault.base.tag;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.vault.base.collections.iterable.VIterables;

import com.google.common.collect.Lists;

public class TaggedCollection<T> {
	private List<TaggedValue<T>> values = Lists.newArrayList();

	public void put(T value, Tag... tags) {
		put(value, Arrays.asList(tags));
	}

	public void put(T value, List<Tag> tags) {
		// FUTURE
	}

	public T get(Tag... tagSelectors) {
		return this.getTaggedValue(Arrays.asList(tagSelectors)).getValue();
	}

	public T get(List<Tag> tagSelectors) {
		return getTaggedValue(tagSelectors).getValue();
	}

	public TaggedValue<T> getTaggedValue(List<Tag> tagSelectors) {
		return VIterables.getSingleResult(select(tagSelectors), true);
	}

	public List<TaggedValue<T>> select(List<Tag> tagSelectors) {
		return this.select(tagSelectors, false);
	}

	public List<TaggedValue<T>> select(List<Tag> tagSelectors, boolean narrow) {
		// FUTURE
		return null;
	}

	public List<TaggedValue<T>> getValues() {
		return Collections.unmodifiableList(values);
	}

	public static class TaggedValue<T> {
		private T value;
		private List<Tag> tags;

		public TaggedValue(T value, List<Tag> tags) {
			this.value = value;
			this.tags = tags;
		}

		public T getValue() {
			return value;
		}

		public List<Tag> getTags() {
			return Collections.unmodifiableList(tags);
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			for (Tag tag : tags) {
				builder.append(tag);
			}

			builder.append(" -> ");
			builder.append(value);
			return builder.toString();
		}
	}
}