package com.rc.spoonbill.test.asserts;

import java.util.Calendar;
import java.util.Date;

import org.junit.Assert;

public class DateAssert {

	public static void assertEqual(Date expected, Date actual) {

		Calendar expectedCalendar = Calendar.getInstance();
		expectedCalendar.setTime(expected);

		Calendar actualCalendar = Calendar.getInstance();
		actualCalendar.setTime(actual);

		Assert.assertEquals(expectedCalendar.get(Calendar.YEAR), actualCalendar.get(Calendar.YEAR));
		Assert.assertEquals(expectedCalendar.get(Calendar.MONTH), actualCalendar.get(Calendar.MONTH));
		Assert.assertEquals(expectedCalendar.get(Calendar.DATE), actualCalendar.get(Calendar.DATE));
	}

	public static void assertNotEqual(Date expected, Date actual) {

		Calendar expectedCalendar = Calendar.getInstance();
		expectedCalendar.setTime(expected);

		Calendar actualCalendar = Calendar.getInstance();
		actualCalendar.setTime(actual);

		if (expectedCalendar.get(Calendar.YEAR) == actualCalendar.get(Calendar.YEAR)
				&& expectedCalendar.get(Calendar.MONTH) == actualCalendar.get(Calendar.MONTH)
				&& expectedCalendar.get(Calendar.DATE) == actualCalendar.get(Calendar.DATE)) {

			Assert.fail("Both date are the same");
		}
	}
}
