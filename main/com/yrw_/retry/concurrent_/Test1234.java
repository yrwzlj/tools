package com.yrw_.retry.concurrent_;

/*
有四个线程1、2、3、4。线程1的功能就是输出1，线程2的功能就是输出2，以此类推.........现在有四个文件ABCD。初始都为空。现要让四个文件呈如下格式：

A：1 2 3 4 1 2....

B：2 3 4 1 2 3....

C：3 4 1 2 3 4....

D：4 1 2 3 4 1....
 */

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 通过 locks 来进行 线程的串行执行
 * list获取到写的文件
 */
public class Test1234 {
    static List<Integer> list = new ArrayList<>();
    static List<FileWriter> os = new ArrayList<FileWriter>();

    static List<Object> locks = new ArrayList<Object>();

    public static void main(String[] args) throws IOException {
        list.add(0);
        list.add(1);
        list.add(2);
        list.add(3);

        ExecutorService executorService = Executors.newFixedThreadPool(4);

        for (int i = 0; i < 4; i++) {
            os.add(new FileWriter("./" + i + ".txt"));
            locks.add(new Object());
        }

        try {
            executorService.execute(new MyThread(1));
            Thread.sleep(1000);

            executorService.execute(new MyThread(2));
            Thread.sleep(1000);

            executorService.execute(new MyThread(3));

            Thread.sleep(1000);
            executorService.execute(new MyThread(4));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    static class MyThread implements Runnable {
        int j = 1;

        public MyThread(int j) {
            this.j = j;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                Integer ele = list.get(j - 1);
                FileWriter fw = os.get(ele);
                ele--;
                if (ele == -1) {
                    ele = 3;
                }
                list.set(j - 1, ele);

                Object cur = locks.get(j - 1);
                Object next = locks.get(j == 4 ? 0 : j);
                synchronized (cur) {
                    try {
                        fw.write(j + " ");
                        fw.flush();
                        synchronized (next) {
                            next.notify();
                        }
                        cur.wait();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
}
