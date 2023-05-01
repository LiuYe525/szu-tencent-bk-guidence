package com.szuse.szubkguidance.listener;

import com.szuse.szubkguidance.constants.MqConstant;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author LiuYe
 * @version 1.0
 * @date 24/4/2023 上午10:49
 */
@Service
public class MessageListener {

    List<String> receiveList;

    public List<String> getReceiveList() {
        return receiveList;
    }

    public MessageListener() {
        this.receiveList = new ArrayList<>();
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = MqConstant.QUEUE_NAME + ".demo"),
            exchange = @Exchange(name = MqConstant.EXCHANGE_NAME, type = ExchangeTypes.TOPIC),
            key = "demo.#"
    ))
    public void MsgListener(String msg) {
        receiveList.add(msg);
    }
}
