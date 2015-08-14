package com.rc.spoonbill.test.asserts;

import java.util.Calendar;
import java.util.Date;

import org.junit.Assert;

public class DateTimeAssert {

	public static void DateTime(Date expected, Date actual) {

		Calendar expectedCalendar = Calendar.getInstance();
		expectedCalendar.setTime(expected);

		Calendar actualCalendar = Calendar.getInstance();
		actualCalendar.setTime(actual);

		Assert.assertEquals(expectedCalendar.get(Calendar.YEAR), actualCalendar.get(Calendar.YEAR));
		Assert.assertEquals(expectedCalendar.get(Calendar.MONTH), actualCalendar.get(Calendar.MONTH));
		Assert.assertEquals(expectedCalendar.get(Calendar.DATE), actualCalendar.get(Calendar.DATE));
		Assert.assertEquals(expectedCalendar.get(Calendar.HOUR), actualCalendar.get(Calendar.HOUR));
		Assert.assertEquals(expectedCalendar.get(Calendar.MINUTE), actualCalendar.get(Calendar.MINUTE));
		Assert.assertEquals(expectedCalendar.get(Calendar.SECOND), actualCalendar.get(Calendar.SECOND));
	}

	public static void Date(Date expected, Date actual) {

		Calendar expectedCalendar = Calendar.getInstance();
		expectedCalendar.setTime(expected);

		Calendar actualCalendar = Calendar.getInstance();
		actualCalendar.setTime(actual);

		Assert.assertEquals(expectedCalendar.get(Calendar.YEAR), actualCalendar.get(Calendar.YEAR));
		Assert.assertEquals(expectedCalendar.get(Calendar.MONTH), actualCalendar.get(Calendar.MONTH));
		Assert.assertEquals(expectedCalendar.get(Calendar.DATE), actualCalendar.get(Calendar.DATE));
	}

	public static void Time(Date expected, Date actual) {

		Calendar expectedCalendar = Calendar.getInstance();
		expectedCalendar.setTime(expected);

		Calendar actualCalendar = Calendar.getInstance();
		actualCalendar.setTime(actual);

		Assert.assertEquals(expectedCalendar.get(Calendar.HOUR), actualCalendar.get(Calendar.HOUR));
		Assert.assertEquals(expectedCalendar.get(Calendar.MINUTE), actualCalendar.get(Calendar.MINUTE));
		Assert.assertEquals(expectedCalendar.get(Calendar.SECOND), actualCalendar.get(Calendar.SECOND));
	}
}
