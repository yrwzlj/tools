package com.yrw_.retry.clone;

class People {
    int age;

    String name;

    public People(int age, String name) {
        this.age = age;
        this.name = name;
    }

    public People(People p) {
        this.age = p.age;
        this.name = p.name;
    }
}