package com.yrw_.retry;

import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Thread.sleep;

public class TestSynchronize {

    static AtomicInteger i = new AtomicInteger(0);

    public static void main(String[] args) {
        TestSynchronize testSynchronize = new TestSynchronize();
        // 对象锁 针对当前对象
        // 非静态方法锁 针对 方法，只允许一个对象进入
        // 静态方法锁 当前类 只有一个 能进入
        //testSynchronize.objSyn();
        //testSynchronize.funcSyn();
        //testSynchronize.deadLock();
        TestSynchronize.staticFuncSyn();
        //testSynchronize.classSyn();
    }

    private void deadLock() {
        TestSynchronize o1 = new TestSynchronize();
        TestSynchronize o2 = new TestSynchronize();
        Thread thread1 = new Thread(() -> {
            synchronized (o1) {
                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("等待o2");
                synchronized (o2) {
                    o2.func();
                }

            }
        });

        Thread thread2 = new Thread(() -> {
            synchronized (o2) {
                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("等待o1");
                synchronized (o1) {
                    o1.func();
                }
            }
        });
        thread1.start();
        thread2.start();
    }

    // 测试对象锁
    private void objSyn() {
        TestSynchronize o = new TestSynchronize();
        Thread thread1 = new Thread(() -> {
            synchronized (o) {
                o.func();
            }
        });

        Thread thread2 = new Thread(() -> {
            synchronized (o) {
                o.func();
            }
        });
        thread1.start();
        thread2.start();
    }

    // 测试非静态方法锁
    private void funcSyn() {
        TestSynchronize testSynchronize = new TestSynchronize();
        Thread thread1 = new Thread(() -> {
            testSynchronize.func();
        });

        Thread thread2 = new Thread(() -> {
            testSynchronize.func();
        });
        thread1.start();
        thread2.start();
    }

    // 类锁
    private void classSyn() {
        // synchronized(object.class) 生效范围
        //  同一个对象 类锁生效
        //  同一个类不同对象，类锁生效
        //  类静态方法 生效

        Thread thread1 = new Thread(() -> {
            TestSynchronize.classSynchronizedInFunc();
        });

        Thread thread2 = new Thread(() -> {
            TestSynchronize.classSynchronizedInFunc();
        });
        thread1.start();
        thread2.start();
    }

    private static void classSynchronizedInFunc() {
        synchronized (TestSynchronize.class) {
            System.out.println(Thread.currentThread().getName());
            try {
                sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void classSynchronized() {
        synchronized (TestSynchronize.class) {
            System.out.println(num++ + Thread.currentThread().getName());
            try {
                sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }



    // 测试静态方法锁
    private static void staticFuncSyn() {
        TestSynchronize testSynchronize = new TestSynchronize();
        Thread thread1 = new Thread(() -> {
            TestSynchronize.staticFunc();
        });

        Thread thread2 = new Thread(() -> {
            TestSynchronize.staticFunc();
        });
        thread1.start();
        thread2.start();
    }

    // 测试静态方法锁
    private synchronized static void staticFunc() {
        System.out.println(Thread.currentThread().getName());
        try {
            sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    volatile Integer num = 1;

    private synchronized void func() {
        System.out.println(num++ + Thread.currentThread().getName());
        try {
            sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
