package com.yrw_.retry.concurrent_;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestABC {

    static Integer o1 = 1;
    static Integer o2 = 2;
    static Integer o3 = 3;

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        executorService.execute(() -> {
            for (int i = 0; i < 10; i++) {
                synchronized (o1) {
                    System.out.println("a");
                    synchronized (o2) {
                        o2.notify();
                    }
                    try {
                        o1.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        executorService.execute(() -> {
            for (int i = 0; i < 10; i++) {
                synchronized (o2) {
                    System.out.println("b");
                    synchronized (o3) {
                        o3.notify();
                    }
                    try {
                        o2.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        executorService.execute(() -> {
            for (int i = 0; i < 10; i++) {
                synchronized (o3) {
                    System.out.println("c");
                    synchronized (o1) {
                        o1.notify();
                    }
                    try {
                        o3.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }
}
