package com.szuse.szubkguidance.controller;

import com.szuse.szubkguidance.constants.MqConstant;
import com.szuse.szubkguidance.entity.User;
import com.szuse.szubkguidance.listener.MessageListener;
import com.szuse.szubkguidance.mapper.UserMapper;
import com.szuse.szubkguidance.utils.MqUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserMapper userMapper;
    private RabbitTemplate rabbitTemplate;

    RedisTemplate<String, Object> redisTemplate;

    MessageListener messageListener;

    @Autowired
    public UserController(UserMapper userMapper, RabbitTemplate rabbitTemplate, @Qualifier("userRedisClient") RedisTemplate<String, Object> redisTemplate, MessageListener messageListener) {
        this.userMapper = userMapper;
        this.rabbitTemplate = rabbitTemplate;
        this.redisTemplate = redisTemplate;
        this.messageListener = messageListener;
    }

    @GetMapping("/all")
    public List<User> findAll() {
        return userMapper.findAll();
    }

    @GetMapping("/setKey/{key}/{value}")
    public String setKey(@PathVariable String key, @PathVariable String value) {
        redisTemplate.opsForValue().set(key, value, 20, TimeUnit.MINUTES);
        return "set redis: " + key + ":" + value;
    }

    @GetMapping("/getKey/{key}")
    public String getKey(@PathVariable String key) {
        return "get redis:" + redisTemplate.opsForValue().get(key);
    }

    @GetMapping("/sendMsg/{msg}")
    public String senMsg(@PathVariable String msg) {
        MqUtils.sendMsg(rabbitTemplate, MqConstant.EXCHANGE_NAME, "demo.test", msg);
        return "发送消息至消息队列成功";
    }

    @GetMapping("/getMsg")
    public String getMsg() {
        return messageListener.getReceiveList().toString();
    }

}