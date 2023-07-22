package com.szuse.event.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
/**
 * @author Lye255
 * @version 1.0
 * @description: rabbit connection configuration
 * @date 2023/7/22 17:28
 */
@Data
public class RabbitConnectionConfig {
    private String host;
    private int port;
    private String username;
    private String password;
    private String virtualHost;

    @Bean
    @ConfigurationProperties(prefix = "spring.rabbitmq")
    public RabbitConnectionConfig rabbitConnectionConfig(){
        return new RabbitConnectionConfig();
    }
}
