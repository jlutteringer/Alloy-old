package org.alloy.metal.test.string;

import java.util.List;
import java.util.Set;

import org.alloy.metal.collections._Set;
import org.alloy.metal.collections.iterable._Iterable;
import org.alloy.metal.collections.lists._List;
import org.alloy.metal.string.StringPlaceholderIterator;
import org.alloy.metal.string.StringPlaceholderIterator.StringPlaceholder;
import org.junit.Assert;
import org.junit.Test;

public class StringPlaceholderIteratorTest {
	@Test
	public void test() {
		List<String> testStrings = _List.list();
		testStrings.add("This is a test string with no placeholders");
		testStrings.add("This is ${a test string with no placeholder${s");
		testStrings.add(null);
		testStrings.add("");
		testStrings.add("${${${");
		testStrings.add("}}");
		testStrings.add("asdfasdf }A asdfa sd} as}");

		List<Set<StringPlaceholder>> testResults = _List.list();
		testResults.add(_Set.set());
		testResults.add(_Set.set());
		testResults.add(_Set.set());
		testResults.add(_Set.set());
		testResults.add(_Set.set());
		testResults.add(_Set.set());
		testResults.add(_Set.set());

		int count = 0;
		for (String testString : testStrings) {
			Set<StringPlaceholder> resultsSet = _Set.set(_Iterable.fromIteratorSupplier(() -> new StringPlaceholderIterator("${", "}", testString)));
			Assert.assertEquals(resultsSet, testResults.get(count));
			count++;
		}
	}
}