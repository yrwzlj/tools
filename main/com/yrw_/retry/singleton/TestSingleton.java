package com.yrw_.retry.singleton;

public class TestSingleton {

    private TestSingleton() {

    }

    // 静态内部类
    private static class Instance {
        public static TestSingleton testSingleton = new TestSingleton();
    }

    public TestSingleton getInstance2(){
        return Instance.testSingleton;
    }

    // 饿汉式
    private static volatile TestSingleton instance;

    // double check
    public TestSingleton getInstance(){
        if (instance == null) {
            synchronized (this) {
                if (instance == null) {
                    instance = new TestSingleton();
                }
            }
        }

        return instance;
    }
}
