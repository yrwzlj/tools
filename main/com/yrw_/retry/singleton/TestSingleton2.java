package com.yrw_.retry.singleton;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * 枚举单例
 * 无法被反射破坏
 */
public enum TestSingleton2 {
    // 单例
    instance;

    public static void main(String[] args) {
        TestSingleton2 instance1 = TestSingleton2.instance;

        Constructor<?>[] constructors = TestSingleton2.class.getDeclaredConstructors();
        for (Constructor constructor : constructors) {
            try {
                TestSingleton2 o = (TestSingleton2) constructor.newInstance();
                System.out.println(o);
                /**
                 Exception in thread "main" java.lang.IllegalArgumentException: Cannot reflectively create enum objects
                 */
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            } finally {
            }
        }
    }
}
