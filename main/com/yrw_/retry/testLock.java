package com.yrw_.retry;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

public class testLock {

    public static void main(String[] args) {
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            set.add(i);
        }
        List<Integer> collect1 = set.stream().sorted((s1, s2) -> s2.compareTo(s1)).collect(Collectors.toList());
        Map<Integer, String> collect = set.stream().sorted((s1, s2) -> s2.compareTo(s1)).collect(Collectors.toMap(s -> {
            return s;
        }, s -> {
            return "xx";
        }));
        System.out.println(collect.toString());
    }

    public void testReentrantWr() {
        ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
        ReentrantReadWriteLock.ReadLock readLock = reentrantReadWriteLock.readLock();
        readLock.lock();
        readLock.unlock();
    }
}
