package org.alloy.metal.string;

public class StringParser {
	private String string;

	public StringParser(String string) {
		this.string = string;
		this.string.toCharArray();
	}

	public StringParserRangeStart range() {
		// FUTURE Auto-generated method stub
		return null;
	}

	public static interface StringParserRange {

	}

	public static class StringParserRangeStart implements StringParserRange {
		public StringParserPostion<StringParserRangeStart> from() {
			// FUTURE Auto-generated method stub
			return null;
		}

		public StringParserRangeEnd from(int i) {
			// FUTURE Auto-generated method stub
			return null;
		}

		public StringParserRangeEnd fromStart() {
			// FUTURE Auto-generated method stub
			return null;
		}
	}

	public static class StringParserRangeEnd implements StringParserRange {
		public StringParserPostion<StringParserFinish> to() {
			// FUTURE Auto-generated method stub
			return null;
		}

		public StringParserFinish toLast(String string) {
			// FUTURE Auto-generated method stub
			return null;
		}
	}

	public static class StringParserPostion<T extends StringParserRange> {
		public T index(int i) {
			// FUTURE Auto-generated method stub
			return null;
		}

		public StringParserPositionDescription<T> match(String string) {
			// FUTURE Auto-generated method stub
			return null;
		}
	}

	public static class StringParserPositionDescription<T extends StringParserRange> {
		public T last() {
			// FUTURE Auto-generated method stub
			return null;
		}
	}

	public static class StringParserFinish implements StringParserRange {
		public String parse() {
			return null;
		}
	}
}
