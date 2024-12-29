package com.yrw_.retry.template;

public abstract class testTemplate {

    public abstract void afterInit();

    public abstract void beforeProceed();

    public final void init() {
        int i = 1;
        afterInit();
    }

    public final void proceed() {
        beforeProceed();
        int j = 2;
    }

    public final void close() {

    }
}
