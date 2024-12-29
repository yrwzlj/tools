package com.yrw_.retry.reference;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.LinkedList;
import java.util.List;

public class Referrence {

    private static final List<Object> LIST = new LinkedList<>();
    private static final ReferenceQueue QUEUE = new ReferenceQueue();

    public static void main(String[] args) {
        PhantomReference<M> m = new PhantomReference<>(new M(), QUEUE);

        new Thread(()->{
            while (true) {
                LIST.add(new byte[1024*1024]);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
                System.out.println(m.get());
            }
        }).start();

        new Thread(()->{
            while (true) {
                Reference poll = QUEUE.poll();
                if(poll != null) {
                    System.out.println("虚引用对象：" + poll + " 被jvm回收了");
                }
            }
        }).start();
    }

   static public class M {
        @Override
        protected void finalize() {
            System.out.println("对象被回收了...");
        }
    }
}

