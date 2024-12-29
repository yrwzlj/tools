package com.yrw_.retry.concurrent_;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 给定一个类，在不查看内部实现的情况下，判断它是否安全
 */
public class TestSafe {

    // 查看 静态 int 是否 线程安全
    static int v = 1;

    static public CountDownLatch countDownLatch = new CountDownLatch(2);


    public int getV() {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return v++;
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);


//        Thread t1 = new Thread(() -> {
//            for (int i = 0; i < 100; i++) {
//
//                v++;
//            }
//
//            countDownLatch.countDown();
//            System.out.println(1 + "执行完成");
//        });
//
//        Thread t2 = new Thread(() -> {
//            for (int i = 0; i < 100; i++) {
//
//                v++;
//            }
//
//            countDownLatch.countDown();
//            System.out.println(1 + "执行完成");
//        });
//
//        t1.start();
//        t2.start();

        executorService.execute(() -> {
            for (int i = 0; i < 100; i++) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                v++;
            }

            countDownLatch.countDown();
            System.out.println(1 + "执行完成");
        });

        executorService.execute(() -> {
            for (int i = 0; i < 100; i++) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                v++;
            }

            countDownLatch.countDown();
            System.out.println(2 + "执行完成");
        });

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(v);
    }
}
