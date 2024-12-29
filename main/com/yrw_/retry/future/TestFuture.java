package com.yrw_.retry.future;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class TestFuture {

    public static void main(String[] args) {
        FutureTask<Integer> futureTask = new FutureTask<Integer>(() -> {
            System.out.println(Thread.currentThread().getName());
            Thread.sleep(10000);
            return 1;
        });
        Thread thread = new Thread(futureTask);
        thread.start();
        try {
            System.out.println(Thread.currentThread().getName());
            Integer integer = futureTask.get(10, TimeUnit.SECONDS);
            System.out.println(integer);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            System.out.println("超时了");
            throw new RuntimeException(e);
        } finally {
        }
    }
}
