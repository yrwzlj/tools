package com.yrw_.retry.sort;

public class StackSort {

    public static void main(String[] args) {
        int[] num = new int[]{1,9,2,10,5};
        int[] stack = buildStack(num);
        //stack 用的vector数组 queue 用数组和链表都可以
        //Stack stack1 = new Stack();
        //Queue<> queue = new ConcurrentLinkedQueue();
    }

    private static int[] buildStack(int[] num) {
        int[] stack = new int[num.length];
        build(num,0);
        return num;
    }

    private static void build(int[] num,int cur) {
        if (cur >= num.length) {
            return;
        }
        build(num,cur * 2 + 1);
        build(num,cur * 2 + 2);
        if (cur * 2 + 1 >= num.length) {
            return;
        }
        if (cur * 2 + 2 >= num.length) {
            num[cur * 2 + 1] = Math.min(num[cur * 2 + 1],num[cur]);
            num[cur] = Math.max(num[cur * 2 + 1],num[cur]);
            return;
        }

        if (num[cur * 2 + 1] < num[cur * 2 + 2]) {
            if (num[cur * 2 + 2] > num[cur]) {
                int temp = num[cur];
                num[cur] = num[cur * 2 + 2];
                num[cur * 2 + 2] = temp;
            }
        } else {
            if (num[cur * 2 + 1] > num[cur]) {
                int temp = num[cur];
                num[cur] = num[cur * 2 + 1];
                num[cur * 2 + 1] = temp;
            }
        }
    }


}
