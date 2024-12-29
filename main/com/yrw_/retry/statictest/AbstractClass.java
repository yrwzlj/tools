package com.yrw_.retry.statictest;

public abstract class AbstractClass {

    static {
        System.out.println("Abs static 1");
    }


    public AbstractClass() {
        System.out.println("Abs create");
        testStatic();
    }

    private static void testStatic() {
        System.out.println("Abs func");
    }

    static  {
        System.out.println("Abs static 2");
    }
}
