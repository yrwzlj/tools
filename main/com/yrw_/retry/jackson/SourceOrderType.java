package com.yrw_.retry.jackson;

public enum SourceOrderType {
    Cruise(0),
    Tour(1);

    private final int value;

    private SourceOrderType(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static SourceOrderType findByValue(int value) {
        switch (value) {
            case 0:
                return Cruise;
            case 1:
                return Tour;
            default:
                return null;
        }
    }
}