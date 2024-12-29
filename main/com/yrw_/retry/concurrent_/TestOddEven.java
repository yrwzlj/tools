package com.yrw_.retry.concurrent_;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestOddEven {

    /**
     * 奇偶数交替打印
     */

    static Integer i = 1;
    static Object lock = new Object();

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        executorService.execute(() -> {
            while (i < 100) {
                synchronized (i) {
                    System.out.println("r1:" + i++);
                    i.notify();
                    try {
                        i.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

        });

        executorService.execute(() -> {
            while (i < 100) {
                synchronized (i) {
                    System.out.println("r2:" + i++);
                    i.notify();
                    try {
                        i.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

        });
    }
}
