package com.szuse.szubkguidance.config;


import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.util.StringUtils;

import java.time.Duration;

/**
 * @Author LiuYe
 * @Date 2023/3/28 20:44
 * @Version 1.0
 */
@EnableCaching
@Configuration
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private int port;
    @Value("${spring.redis.password}")
    private String password;
    @Value("${spring.redis.lettuce.pool.max-active}")
    private int maxActive;
    @Value("${spring.redis.lettuce.pool.max-idle}")
    private int maxIdle;
    @Value("${spring.redis.lettuce.pool.min-idle}")
    private int minIdle;
    @Value("${spring.redis.lettuce.pool.max-wait}")
    private int maxWait;
    @Value("${spring.redis.timeout}")
    private int timeout;

    @Bean(name = "userRedisClient")
    public RedisTemplate<String, Object> userRedisTemplate(@Value("${spring.redis.database.user}") int database) {
        return generateTemplate(database);
    }

    @Bean(name = "articleRedisClient")
    public RedisTemplate<String, Object> articleRedisTemplate(@Value("${spring.redis.database.article}") int database) {
        return generateTemplate(database);
    }

    @Bean(name = "tagRedisClient")
    public RedisTemplate<String, Object> tagRedisTemplate(@Value("${spring.redis.database.tag}") int database) {
        return generateTemplate(database);
    }

    public RedisTemplate generateTemplate(int databaseIndex) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory(databaseIndex));
        // 创建JSON序列化工具
        GenericJackson2JsonRedisSerializer jsonRedisSerializer =
                new GenericJackson2JsonRedisSerializer();
        // 设置Key的序列化
        template.setKeySerializer(jsonRedisSerializer);
        template.setHashKeySerializer(jsonRedisSerializer);
        // 设置Value的序列化
        template.setValueSerializer(jsonRedisSerializer);
        template.setHashValueSerializer(jsonRedisSerializer);
        return template;
    }

    public RedisConnectionFactory connectionFactory(int database) {

        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName(host);
        configuration.setPort(port);
        if (StringUtils.hasText(password)) {
            configuration.setPassword(password);
        }
        configuration.setDatabase(database);

        GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();
        genericObjectPoolConfig.setMaxIdle(maxIdle);
        genericObjectPoolConfig.setMinIdle(minIdle);
        genericObjectPoolConfig.setMaxTotal(maxActive);

        LettuceClientConfiguration clientConfig = LettucePoolingClientConfiguration.builder()
                .commandTimeout(Duration.ofMillis(timeout))
                .poolConfig(genericObjectPoolConfig)
                .build();

        LettuceConnectionFactory lettuce = new LettuceConnectionFactory(configuration, clientConfig);
        lettuce.afterPropertiesSet();
        return lettuce;
    }

}
