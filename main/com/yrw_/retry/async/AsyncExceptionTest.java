package com.yrw_.retry.async;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * @Author: rw.yang
 * @DateTime: 2025/8/12
 **/
public class AsyncExceptionTest {

    public static void main(String[] args) {
        ExecutorService executorService = java.util.concurrent.Executors.newFixedThreadPool(10);

        Future<?> submit = executorService.submit(() -> {
            try {
                // 模拟异步任务
                Thread.sleep(1000);
                System.out.println("异步任务执行成功");
                List<String> list = new ArrayList<>();
                System.out.println(list.get(1));
            } catch (InterruptedException e) {
                System.out.println("异步任务被中断: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("异步任务发生异常: " + e.getMessage());
                throw e;
            }
        });

        try {
            submit.get(1L, java.util.concurrent.TimeUnit.SECONDS);
        } catch (Exception e) {
            System.out.println("主线程捕获到异常: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
