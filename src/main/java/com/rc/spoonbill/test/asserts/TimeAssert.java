package com.rc.spoonbill.test.asserts;

import java.util.Calendar;
import java.util.Date;

import org.junit.Assert;

public class TimeAssert {

	public static void assertEqual(Date expected, Date actual) {

		Calendar expectedCalendar = Calendar.getInstance();
		expectedCalendar.setTime(expected);

		Calendar actualCalendar = Calendar.getInstance();
		actualCalendar.setTime(actual);

		Assert.assertEquals(expectedCalendar.get(Calendar.HOUR), actualCalendar.get(Calendar.HOUR));
		Assert.assertEquals(expectedCalendar.get(Calendar.MINUTE), actualCalendar.get(Calendar.MINUTE));
		Assert.assertEquals(expectedCalendar.get(Calendar.SECOND), actualCalendar.get(Calendar.SECOND));
	}
}
