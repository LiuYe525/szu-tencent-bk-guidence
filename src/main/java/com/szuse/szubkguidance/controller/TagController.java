package com.szuse.szubkguidance.controller;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author LiuYe
 * @version 1.0
 * @date 1/5/2023 下午9:29
 */
@RestController
@RequestMapping("/tag")
public class TagController {

    @Resource(name = "tagRedisClient")
    private RedisTemplate redisTemplate;

    @GetMapping("/setKey/{key}/{value}")
    public String setKey(@PathVariable String key, @PathVariable String value) {
        redisTemplate.opsForValue().set(key, value, 20, TimeUnit.MINUTES);
        return "set redis: " + key + ":" + value;
    }

    @GetMapping("/getKey/{key}")
    public String getKey(@PathVariable String key) {
        return "get redis:" + redisTemplate.opsForValue().get(key);
    }
}
