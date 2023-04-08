package com.szuse.szubkguidance.controller;

import com.szuse.szubkguidance.entity.User;
import com.szuse.szubkguidance.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private UserMapper userMapper;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    RedisTemplate<String,Object> redisTemplate;

    @GetMapping("/all")
    public List<User> findAll(){
        return userMapper.findAll();
    }

    @GetMapping("/setKey/{key}/{value}")
    public String setKey(@PathVariable String key,@PathVariable String value) {
        redisTemplate.opsForValue().set(key,value);
        return "set redis: " + key + ":" + value;
    }

    @GetMapping("/getKey/{key}")
    public String getKey(@PathVariable String key) {
        return "get redis:" + redisTemplate.opsForValue().get(key);
    }

}