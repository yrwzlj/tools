package com.yrw_.retry.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.yrw_.retry.TestAtomic;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
public class Student {

    int i;

    Inner inner;

    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(1);
        int i = atomicInteger.incrementAndGet();
        System.out.println(i);

        String regrex = "\\d+";
        String s = "fee record 123";
        Pattern compile = Pattern.compile(regrex);
        Matcher matcher = compile.matcher(s);
        if (matcher.find()) {
            String group = matcher.group();
            System.out.println(group);
        }

        Inner inner = new Inner();
        inner.list.add("ahaha");
        System.out.println(JSON.toJSONString(inner));
        Student student = new Student();
        student.i = 1;
        student.inner = inner;
        JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(student));
        JSONObject jsonObject1 = JSONObject.parseObject(JSON.toJSONString(jsonObject.get("inner")));
        List<String> list = (List<String>) jsonObject1.get("list");
        System.out.println(list.get(0));
    }
}
