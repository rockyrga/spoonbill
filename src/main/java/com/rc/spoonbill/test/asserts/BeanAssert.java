package com.rc.spoonbill.test.asserts;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

public class BeanAssert {
    public static void assertEqual(Object object1, Object object2, String... properties)
            throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {

        for (String property : properties) {

            Object value1 = BeanUtils.getProperty(object1, property);
            Object value2 = BeanUtils.getProperty(object2, property);

            assertEquals(value1, value2);
        }
    }
}
