package com.yrw_.retry.sqel;

import lombok.Data;

/**
 * @Author: rw.yang
 * @DateTime: 2025/1/25
 **/
@Data
public class User {

    private String id;

    private String[] names;

    public User(String id, String[] name) {
        this.id = id;
        this.names = name;
    }
}
