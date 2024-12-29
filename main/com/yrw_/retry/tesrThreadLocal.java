package com.yrw_.retry;

import org.springframework.boot.web.servlet.server.Session;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class tesrThreadLocal {

    private static ThreadLocal<Session> localSession;

    public static void main(String[] args) {
        ThreadLocal.withInitial(() -> 6);
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 10, 1L, TimeUnit.MICROSECONDS,
                new ArrayBlockingQueue<Runnable>(10), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                return thread;
            }
        }, new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                executor.execute(r);
            }
        });
        ThreadLocal threadLocal = new InheritableThreadLocal();
        threadLocal.set("老王");
        ThreadLocal threadLocal2 = new ThreadLocal();
        threadLocal2.set("老王");
        new Thread(() -> {
            System.out.println(threadLocal.get());
            System.out.println(threadLocal2.get());
            System.out.println(threadLocal.get().equals(threadLocal2.get()));
        }).start();
    }

    public static void test() {
    }
}
