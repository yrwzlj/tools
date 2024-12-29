package com.yrw_.retry.excel;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Product implements Serializable {

    String 商品编码;

    String 卷烟名称;

    String 批发价;

    String 价类;

    Map<String, Integer> map = new HashMap<>();

    @Override
    public String toString() {
        return "id" + 商品编码 + " name:" + 卷烟名称 + " pf:" + 批发价 + "price:" + 价类 + map;
    }
}
