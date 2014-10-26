package org.alloy.metal.string;

public class _String {
	public static final String CHARACTER_ENCODING = "UTF-8";

	public static String substringBetween(String string, String start, String end) {
		// FUTURE
		return string;
	}

	public static String substringBetween(String string, String start, String end, int nthOccurence) {
		// FUTURE
		return string;
	}

	public static Stringifier stringify(Object object) {
		// FUTURE Auto-generated method stub
		return null;
	}

	public static StringParser parse(String string) {
		return new StringParser(string);
	}

	// FUTURE this could probably be factored better
	private static final char DELIM_START = '{';
	private static final char DELIM_STOP = '}';
	private static final char ESCAPE_CHAR = '\\';

	public static String resolvePlaceholders(String message, Object[] parameters) {
		if (message == null || parameters == null || parameters.length == 0) {
			return message;
		}

		final StringBuilder result = new StringBuilder();
		int escapeCounter = 0;
		int currentArgument = 0;
		for (int i = 0; i < message.length(); i++) {
			final char curChar = message.charAt(i);
			if (curChar == ESCAPE_CHAR) {
				escapeCounter++;
			} else {
				if (curChar == DELIM_START && i < message.length() - 1
						&& message.charAt(i + 1) == DELIM_STOP) {
					// write escaped escape chars
					final int escapedEscapes = escapeCounter / 2;
					for (int j = 0; j < escapedEscapes; j++) {
						result.append(ESCAPE_CHAR);
					}

					if (escapeCounter % 2 == 1) {
						// i.e. escaped
						// write escaped escape chars
						result.append(DELIM_START);
						result.append(DELIM_STOP);
					} else {
						// unescaped
						if (currentArgument < parameters.length) {
							result.append(parameters[currentArgument]);
						} else {
							result.append(DELIM_START).append(DELIM_STOP);
						}
						currentArgument++;
					}
					i++;
					escapeCounter = 0;
					continue;
				}
				// any other char beside ESCAPE or DELIM_START/STOP-combo
				// write unescaped escape chars
				if (escapeCounter > 0) {
					for (int j = 0; j < escapeCounter; j++) {
						result.append(ESCAPE_CHAR);
					}
					escapeCounter = 0;
				}
				result.append(curChar);
			}
		}
		return result.toString();
	}
}