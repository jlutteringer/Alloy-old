package org.alloy.metal.string;

import java.io.Serializable;
import java.util.NoSuchElementException;

import org.alloy.metal.collections.iterable.SingleEntryIterator;
import org.alloy.metal.string.StringPlaceholderIterator.StringPlaceholder;
import org.alloy.metal.utilities._Precondition;

public class StringPlaceholderIterator extends SingleEntryIterator<StringPlaceholder> {
	private final String placeholderPrefix;
	private final String placeholderSuffix;
	private final String source;

	private int currentLocation = 0;

	public StringPlaceholderIterator(String placeholderPrefix, String placeholderSuffix, String source) {
		_Precondition.notNull(placeholderPrefix, placeholderSuffix);

		this.placeholderPrefix = placeholderPrefix;
		this.placeholderSuffix = placeholderSuffix;
		this.source = _String.getDefault(source);
	}

	@Override
	protected StringPlaceholder generateNext() throws NoSuchElementException {
		int sourceLength = source.length();

		int placeholderPrefixIndex = source.indexOf(placeholderPrefix, currentLocation);
		if (placeholderPrefixIndex == -1) {
			throw new NoSuchElementException();
		}

		int placeholderSuffixIndex = source.indexOf(placeholderSuffix, currentLocation);
		if (placeholderPrefixIndex == -1) {
			throw new NoSuchElementException();
		}

		throw new NoSuchElementException();
	}

	public static class StringPlaceholder implements Serializable {
		private static final long serialVersionUID = -8252126400328088545L;

		private final String name;
		private final String source;
		private final int sourceIndex;

		public StringPlaceholder(String name, String source, int sourceIndex) {
			super();
			this.name = name;
			this.source = source;
			this.sourceIndex = sourceIndex;
		}

		public String getName() {
			return name;
		}

		public String getSource() {
			return source;
		}

		public int getSourceIndex() {
			return sourceIndex;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			result = prime * result + ((source == null) ? 0 : source.hashCode());
			result = prime * result + sourceIndex;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			StringPlaceholder other = (StringPlaceholder) obj;
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			if (source == null) {
				if (other.source != null)
					return false;
			} else if (!source.equals(other.source))
				return false;
			if (sourceIndex != other.sourceIndex)
				return false;
			return true;
		}
	}
}