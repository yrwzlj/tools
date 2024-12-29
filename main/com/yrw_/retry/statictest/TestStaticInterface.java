package com.yrw_.retry.statictest;

public interface TestStaticInterface {
    default int convertToInt(String s) {
        return 1;
    }

    static int cpnbvert() {
        return 1;
    }
}
