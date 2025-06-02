package com.yrw_.retry.threadlocal;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: rw.yang
 * @DateTime: 2025/1/23
 **/
public class ThreadLocalTest {

    private ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static void main(String[] args) {
        try {
            ThreadLocalTest threadLocalTest = new ThreadLocalTest();
            threadLocalTest.throwEx();
        } finally {
            System.out.println(2);
        }

        System.out.println(3);
    }

    public void throwEx() {
        List<String> list = new ArrayList<>();
        System.out.println(list.get(1));
    }
}
