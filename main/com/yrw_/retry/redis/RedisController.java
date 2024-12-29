package com.yrw_.retry.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
public class RedisController {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @PostMapping("redis/set")
    public String redisSet(String key, Integer expire) {
        stringRedisTemplate.opsForValue().set(key, expire + "", expire, TimeUnit.SECONDS);
        return key;
    }
}
