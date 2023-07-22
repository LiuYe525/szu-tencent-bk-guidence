package com.szuse.event.config;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

/**
 * @author Lye255
 * @version 1.0
 * @description: rabbit configuration
 * @date 2023/7/22 17:28
 */
@Import({RabbitConnectionConfig.class})
public class RabbitConfig {

    /***
     * 创建 RabbitMQ 连接工厂
     * @param rabbitConnectionConfig
     * @return org.springframework.amqp.rabbit.connection.ConnectionFactory
     * @author Lye255
     * @date 2023/7/22 17:20
     */
    @Bean
    public ConnectionFactory connectionFactory(RabbitConnectionConfig rabbitConnectionConfig) {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setAddresses(
                rabbitConnectionConfig.getHost() + ":" + rabbitConnectionConfig.getPort());
        connectionFactory.setUsername(rabbitConnectionConfig.getUsername());
        connectionFactory.setPassword(rabbitConnectionConfig.getPassword());
        connectionFactory.setVirtualHost(rabbitConnectionConfig.getVirtualHost());
        return connectionFactory;
    }

    /***
     * 创建 RabbitAdmin 类，这个类封装了对 RabbitMQ 管理端的操作
     * @param connectionFactory
     * @return org.springframework.amqp.rabbit.core.RabbitAdmin
     * @author Lye255
     * @date 2023/7/22 17:21
     */
    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        rabbitAdmin.setAutoStartup(true);
        return rabbitAdmin;
    }
}
