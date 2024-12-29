package com.yrw_.retry.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

public class RedisTaskQueue {

    @Autowired
    StringRedisTemplate stringRedisTemplate;


}
