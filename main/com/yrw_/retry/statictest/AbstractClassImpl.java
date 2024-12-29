package com.yrw_.retry.statictest;

public class AbstractClassImpl extends AbstractClass{

    static {
        System.out.println("Impl static 1");
    }


    public AbstractClassImpl() {
        System.out.println("Impl create");
        testStatic();
    }

    private static void testStatic() {
        System.out.println("Impl func");
    }

    static  {
        System.out.println("Impl static 2");
    }
}
