package com.szuse.user.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.szuse.common.base.controller.BaseCrudController;
import com.szuse.common.base.result.UnwrappedResultVo;
import com.szuse.common.constant.MqConstant;
import com.szuse.user.dto.UserDTO;
import com.szuse.user.entity.User;
import com.szuse.user.listener.MessageListener;
import com.szuse.user.service.IUserService;
import com.szuse.user.utils.MqUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.sql.Wrapper;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController extends BaseCrudController<User> {

    private final IUserService userService;
    private final RabbitTemplate rabbitTemplate;
    private final RedisTemplate redisTemplate;

    private final MessageListener messageListener;

    public UserController(IUserService userService, RabbitTemplate rabbitTemplate, MessageListener messageListener) {
        super(userService);
        this.userService = userService;
        this.rabbitTemplate = rabbitTemplate;
        this.redisTemplate = userService.getRedisTemplate();
        this.messageListener = messageListener;
    }

    @GetMapping("/all")
    public List<User> findAll() {
        return userService.list();
    }

    @PostMapping
    public UnwrappedResultVo<User> save(@Validated @RequestBody UserDTO dto) {
        User user = dto.dtoToEntity();
        boolean success = userService.save(user);
        if (!success) {
            return UnwrappedResultVo.error().msg("新增失败");
        }
        return UnwrappedResultVo.success().data(user).msg("新增成功");
    }

    @GetMapping("/setKey/{key}/{value}")
    public String setKey(@PathVariable String key, @PathVariable String value) {
        redisTemplate.opsForValue().set(key, value);
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