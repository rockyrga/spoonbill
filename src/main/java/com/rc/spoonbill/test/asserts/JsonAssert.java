package com.rc.spoonbill.test.asserts;

import org.junit.Assert;
import org.springframework.util.StringUtils;

public class JsonAssert {

	public static void assertEqual(String expected, String actual) {

		expected = StringUtils.replace(expected, "\\\"", "").trim();
		actual = StringUtils.replace(actual, "\\\"", "").trim();

		Assert.assertEquals(expected, actual);
	}
}
