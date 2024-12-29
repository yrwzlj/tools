package com.yrw_.retry.json;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Inner {

    List<String> list = new ArrayList<>();
    int i = 0;
}
