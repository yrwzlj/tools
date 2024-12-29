package com.yrw_.retry.clone;

public class TestClone {

    class Clone extends Object implements Cloneable{
        People p;

        @Override
        public Clone clone() throws CloneNotSupportedException {
            Clone clone = (Clone) super.clone();
            clone.p = new People(this.p);
            return clone;
        }

        Clone getClone() {
            try {
                return (Clone) super.clone();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
        }


        @Override
        public String toString() {
            return "age:" + p.age + " name:" + p.name;
        }
    }

    public Clone getClone() {
        return new Clone();
    }

    class NoClone extends Object{
        People p;

        @Override
        public NoClone clone() throws CloneNotSupportedException {
            return (NoClone) super.clone();
        }
    }

    public NoClone getNoClone() {
        return new NoClone();
    }

    /**
     * 不实现cloneable接口无法使用clone方法
     *
     * 默认clone为浅拷贝，实现深拷贝需要自定义clone方法
     */
    public static void main(String[] args) {
        TestClone testClone = new TestClone();
        Clone clone = testClone.getClone();
        NoClone noClone = testClone.getNoClone();

        clone.p = new People(1,"oo");
        Clone clone1 = clone.getClone();
        clone1.p.age = 2;
        System.out.println("默认clone");
        System.out.println(clone.toString());
        System.out.println(clone1.toString());

        try {
            Clone clone2 = clone.clone();
            clone2.p.age = 3;
            System.out.println("自定义clone");
            System.out.println(clone.toString());
            System.out.println(clone2.toString());
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }

        //Object clone2 = noClone.clone();
    }
}
