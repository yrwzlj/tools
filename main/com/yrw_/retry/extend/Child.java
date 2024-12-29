package com.yrw_.retry.extend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

public class Child extends Parent{

    Child() {
        super(1);
    }

    public static void main(String[] args) {
        Child child = new Child();
        child.hashCode();
        HashMap<Integer,Integer> map = new HashMap<>();
        Hashtable<Integer,Integer> hashtable = new Hashtable<>();
        hashtable.put(1,1);
        hashtable.get(1);
        map.put(null,null);
    }

    @Override
    public void testProtected() {
        super.testProtected();
    }

    @Override
    public void testBlank() {
        super.testBlank();
    }

    @Override
    public void testPublic() {
        super.testPublic();
    }

//    @Override
//    public void testParam(Set<Integer> collection, List<Integer> arrayList) {
//        super.testParam(collection, arrayList);
//    }

    @Override
    public ArrayList<Integer> testReturn() {
        return new ArrayList<>();
    }

    @Override
    void testExeption() throws NullPointerException {

    }

    void testParam(Double num) {

    }

    void testParam(Integer num, Double score) {

    }
}
