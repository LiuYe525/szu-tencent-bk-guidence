package com.szuse.szubkguidance;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

@SpringBootTest
class SzuBkGuidanceApplicationTests {

    @Value("${spring.redis.host}")
    private String host;
    @Resource(name = "userRedisClient")
    private RedisTemplate userRedisClient;
    @Resource(name = "articleRedisClient")
    private RedisTemplate articleRedisClient;
    @Resource(name = "tagRedisClient")
    private RedisTemplate tagRedisClient;

    @Test
    void contextLoads() {
        userRedisClient.opsForValue().set("user", "test");
        articleRedisClient.opsForValue().set("article", "test");
        tagRedisClient.opsForValue().set("tag", "test");
    }

}
