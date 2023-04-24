package com.szuse.szubkguidance.utils;

import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * @author LiuYe
 * @version 1.0
 * @date 29/3/2023 下午2:43
 */
public class MqUtils {
    public static <T> void sendMsg(RabbitTemplate rabbitTemplate, String exchangeName, String queueKey, T message) {
        rabbitTemplate.convertAndSend(exchangeName, queueKey, message);
    }
}
