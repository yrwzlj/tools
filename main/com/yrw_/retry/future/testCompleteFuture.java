package com.yrw_.retry.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class testCompleteFuture {

    public static void main(String[] args) {
        try {
            testCompleteFuture testCompleteFuture = new testCompleteFuture();
            testCompleteFuture.thenApplyAsync();
            testCompleteFuture.allOf();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 有返回值
     * supplyAsync
     * 结果：
     * supplyAsync.................start.....
     * 当前线程：33
     * 运行结果：5
     * supplyAsync.................end.....5
     */
    public void supplyAsync() throws ExecutionException, InterruptedException {
        System.out.println("supplyAsync.................start.....");
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("当前线程：" + Thread.currentThread().getId());
            int i = 10 / 2;
            System.out.println("运行结果：" + i);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return i;
        });
        System.out.println("supplyAsync.................end....." + completableFuture.get());
    }

    /**
     * 上面任务执行完执行+可以拿到结果+可以返回值
     * 结果：
     *  thenApplyAsync.................start.....
     *  thenApplyAsync当前线程：10
     *  thenApplyAsync运行结果：5
     *  thenApplyAsync_get_before.....
     *  thenApplyAsync任务2启动了。。。。。上步结果：5
     *  thenApplyAsync.................end.....hello10
     *  thenApplyAsync_get_after
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public void thenApplyAsync() throws ExecutionException, InterruptedException {
        System.out.println("thenApplyAsync.................start.....");
        CompletableFuture<String> thenApplyAsync = CompletableFuture.supplyAsync(() -> {
            System.out.println("thenApplyAsync当前线程：" + Thread.currentThread().getId());
            int i = 10 / 2;
            System.out.println("thenApplyAsync运行结果：" + i);
            return i;
        }).thenApplyAsync(result -> {
            System.out.println("thenApplyAsync任务2启动了。。。。。上步结果：" + result);
            return "hello" + result * 2;
        });
        System.out.println("thenApplyAsync_get_before.....");
        System.out.println("thenApplyAsync.................end....." + thenApplyAsync.get());
        System.out.println("thenApplyAsync_get_after");
    }

    /**
     * 多任务组合
     * allOf 等待所有任务完成
     * 结果：
     * 任务1
     * 任务3
     * 任务2
     * allOf任务1-------任务2-------任务3
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public void allOf() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("任务1");
            return "任务1";
        });
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
                System.out.println("任务2");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "任务2";
        });
        CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> {
            System.out.println("任务3");
            return "任务3";
        });

        CompletableFuture<Void> allOf = CompletableFuture.allOf(future1, future2, future3);
        //等待所有任务完成
        //allOf.get();
        allOf.join();
        System.out.println("allOf" + future1.get() + "-------" + future2.get() + "-------" + future3.get());

    }

}
