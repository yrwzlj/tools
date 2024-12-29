package com.yrw_.retry.concurrent_;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 死锁实现
 */
public class TestDeadLock {

    static Object o1 = new Object();
    static Object o2 = new Object();

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        // 正确写法
//        executorService.execute(() -> {
//            synchronized (o1) {
//                System.out.println("o1开始啦");
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//                synchronized (o2) {
//                    System.out.println("o1进入o2");
//                }
//            }
//        });
//
//        executorService.execute(() -> {
//            synchronized (o2) {
//                System.out.println("o2开始啦");
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//                synchronized (o1) {
//                    System.out.println("o2进入o1");
//                }
//            }
//        });

        /**
         * wait 方法会释放锁，使当前线程进入阻塞状态，但是外面o1的锁是无法释放的
         */

        executorService.execute(() -> {
            synchronized (o1) {
                System.out.println("o1开始啦");
                o1.notify();
                synchronized (o2) {
                    System.out.println("o1进入o2");
                    try {
                        o2.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        executorService.execute(() -> {
            synchronized (o2) {
                System.out.println("o2开始啦");
                o2.notify();
                synchronized (o1) {
                    System.out.println("o2进入o1");
                    try {
                        o1.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        executorService.execute(() -> {
            synchronized (o1) {
                System.out.println("o3开始啦");
                synchronized (o2) {
                    System.out.println("o3进入o2");
                }
            }
        });
    }
}
