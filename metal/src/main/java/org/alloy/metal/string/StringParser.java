package org.alloy.metal.string;

public class StringParser {
	private String string;

	public StringParser(String string) {
		this.string = string;
		this.string.toCharArray();
	}

	public StringParserRangeStart range() {
		// TODO Auto-generated method stub
		return null;
	}

	public static interface StringParserRange {

	}

	public static class StringParserRangeStart implements StringParserRange {
		public StringParserPostion<StringParserRangeStart> from() {
			// TODO Auto-generated method stub
			return null;
		}

		public StringParserRangeEnd from(int i) {
			// TODO Auto-generated method stub
			return null;
		}

		public StringParserRangeEnd fromStart() {
			// TODO Auto-generated method stub
			return null;
		}
	}

	public static class StringParserRangeEnd implements StringParserRange {
		public StringParserPostion<StringParserFinish> to() {
			// TODO Auto-generated method stub
			return null;
		}

		public StringParserFinish toLast(String string) {
			// TODO Auto-generated method stub
			return null;
		}
	}

	public static class StringParserPostion<T extends StringParserRange> {
		public T index(int i) {
			// TODO Auto-generated method stub
			return null;
		}

		public StringParserPositionDescription<T> match(String string) {
			// TODO Auto-generated method stub
			return null;
		}
	}

	public static class StringParserPositionDescription<T extends StringParserRange> {
		public T last() {
			// TODO Auto-generated method stub
			return null;
		}
	}

	public static class StringParserFinish implements StringParserRange {
		public String parse() {
			return null;
		}
	}
}
