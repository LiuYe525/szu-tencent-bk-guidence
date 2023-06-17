package com.szuse.common.constant;

/**
 * @author LiuYe
 * @version 1.0
 * @date 2/5/2023 下午8:41
 */
public enum RedisConstant {
    User("cache-user");

    RedisConstant(String prefix) {
        this.prefix = prefix;
    }

    private final String prefix;
    public <ID> String getKey(ID id) {
        return prefix + "-" + id;
    }
}
