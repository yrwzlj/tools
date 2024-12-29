package com.yrw_.retry;

public class TestTry {

    public static void main(String[] args) {
        int testTry = testTry(0);
        System.out.println("返回:" + testTry);
        Long a1 = 10L;
        Long a2 = 10L;
        boolean b = a1 / 1000 == a2 / 1000;
        System.out.println(b);
        System.out.println("a1 == a2:"  + (a1 == a2));


        StringBuffer stringBuffer = new StringBuffer();
        String s = "1";

        int i = 4;
        switch (i) {
            case 4 :System.out.println(1);
            case  2 :System.out.println(2);
            default:
                System.out.println("default");
        }
    }


    public int testTry2(int x) {
        try {
            x = 0;
            //int i = 1 / 0;
            System.out.println("after exe");
            return x;
        } catch (Exception e) {
            System.out.println("catch e");
            throw e;
        } finally {
            x++;
        }
    }

    public static int testTry(int x) {
        try {
            x = 0;
            //int i = 1 / 0;
            System.out.println("after exe");
            return x;
        } catch (Exception e) {
            System.out.println("catch e");
            throw e;
        } finally {
            x++;
        }
    }
}
