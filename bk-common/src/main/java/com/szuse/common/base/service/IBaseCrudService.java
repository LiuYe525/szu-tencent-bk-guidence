package com.szuse.common.base.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author LiuYe
 * @version 1.0
 * @date 4/4/2023 下午10:11
 * Entity 实体类型
 * Element User类型
 * T userId类型
 */
public interface IBaseCrudService<Entity> extends IService<Entity> {
    RedisTemplate getRedisTemplate();
}
