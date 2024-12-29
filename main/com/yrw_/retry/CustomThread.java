package com.yrw_.retry;

import com.yrw_.retry.statictest.AbstractClassImpl;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CustomThread {

    public static void main(String[] args) {
        new AbstractClassImpl();
        //scheduled();
        // customExecutor();

    }

    private static void customExecutor() {
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
        Future<Object> submit = threadPoolExecutor.submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                return Thread.currentThread().getName();
            }
        });
        while (true) {
            if (submit.isDone()) {
                System.out.println(System.currentTimeMillis() / 1000);
                break;
            }
        }
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println(System.currentTimeMillis() / 1000);
            }
        },1000);
    }

    private static void scheduled() {
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(5, new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r);
            }
        }, new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                executor.execute(r);
            }
        });
        scheduledThreadPoolExecutor.schedule(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                System.out.println(System.currentTimeMillis() / 1000);
                return System.currentTimeMillis() / 1000;
            }
        }, 10, TimeUnit.SECONDS);
    }

    private static void timer() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("delay");
            }
        }, 10L, 10L);
    }



}
