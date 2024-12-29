package com.yrw_.retry;



public class Bitmap {
    int[] bitmap;
    void init(int n) {
        bitmap =  new int[n];
    }

    int[] add(int n) {
        int index = n / 8;
        int i = 1 << (n % 8);
        bitmap[index] |= i;
        return bitmap;
    }

    public static void main(String[] args) {
        Bitmap bitmap = new Bitmap();
        bitmap.init(16);
        int[] add = bitmap.add(7);
        System.out.println(1);
    }
}
