package com.yrw_.retry.serializable;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;

public class Child extends Parent implements Serializable {

    int i1;

    Date date;

    Child() {
        super(111);
    }

    /**
     * 父类必须提供无参构造器
     * 反序列化出来的对象，父类属性都是默认的
     *
     * 父类实现序列化接口，子类同样含有
     *
     * SeriUID 标识 类的版本，当其固定后，类更改了，反序列化也能成功
     */
    public static void main(String[] args) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("./seri.txt");
            ObjectOutputStream oo = new ObjectOutputStream(fileOutputStream);
            Child child = new Child();
            child.i1 = 2;
            child.date = new Date();
            child.p = 11;
            child.pVal = "parent";
            oo.writeObject(child);
            oo.flush();
            System.out.println("序列化前:" + child);

            FileInputStream fs = new FileInputStream("./seri.txt");
            ObjectInputStream oi = new ObjectInputStream(fs);
            Child o = (Child) oi.readObject();
            System.out.println("反序列化:" + o);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return super.toString() + "i1:" + i1 + " " + "date:" + date;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
