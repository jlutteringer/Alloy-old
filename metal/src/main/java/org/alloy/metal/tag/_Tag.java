package org.alloy.metal.tag;

import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

import org.alloy.metal.function._Predicate;
import org.alloy.metal.tag.TaggedCollection.TaggedValue;

public class _Tag {
	public static Predicate<Tag> filter(Tag selector) {
		return _Predicate.build(Tag.class)
				.and((input) -> input.getName().equals(selector.getName()))
				.continueIf(_Predicate.isDefined())
				.and((input) -> input.getValue().equals(selector.getValue().get()))
				.finish();
	}

	public static Tag tag(String name, Object value) {
		return new Tag(name, value);
	}

	public static <T> TagMapBuilder<T> builder(Class<T> clazz) {
		return new TagMapBuilder<T>();
	}

	public static <T> void print(TaggedCollection<T> map, PrintStream out) {
		print(map.getValues(), out);
	}

	public static <T> void print(List<TaggedValue<T>> taggedValues, PrintStream out) {
		for (TaggedValue<?> taggedValue : taggedValues) {
			out.println(taggedValue.toString());
		}
	}

	public static class TagMapBuilder<T> {
		private LinkedList<Tag> currentTags = new LinkedList<>();
		private TaggedCollection<T> tagMap = new TaggedCollection<>();

		public TagMapBuilder<T> tag(Tag tag) {
			currentTags.push(tag);
			return this;
		}

		public TagMapBuilder<T> put(T value) {
			tagMap.put(value, currentTags);
			return this;
		}

		public TagMapBuilder<T> put(Tag tag, T value) {
			this.tag(tag).put(value).untag();
			return this;
		}

		public TagMapBuilder<T> untag() {
			currentTags.pop();
			return this;
		}

		public TaggedCollection<T> unwrap() {
			return tagMap;
		}

		public List<Tag> getTags() {
			return currentTags;
		}
	}
}