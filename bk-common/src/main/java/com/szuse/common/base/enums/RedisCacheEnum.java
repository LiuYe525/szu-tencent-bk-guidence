package com.szuse.common.base.enums;

import com.szuse.common.base.entity.BaseEntity;

import java.io.Serializable;

/**
 * @author LiuYe
 * @version 1.0
 * @date 6/5/2023 下午3:52
 */
public enum RedisCacheEnum {
    User("cache-user"),
    TAG("cache-tag"),
    RANKING("ranking"),
    LIKE("like"),
    COLLECT("collect"),
    BINDING("binding"),
    STATUS("status");

    RedisCacheEnum(Serializable prefix) {
        this.prefix = prefix;
    }

    private final Serializable prefix;

    public Serializable getKeyById(Serializable id) {
        return prefix + "-" + id;
    }

    public Serializable getKeyByClass(Class<?> clazz) {
        return prefix + "-" + clazz.getSimpleName();
    }

    public Serializable getKey(Class<?> clazz, Serializable id) {
        return prefix + "-" + clazz.getSimpleName() + "-" + id;
    }

    public static <KEY extends Serializable> Serializable getKeyByClass(Class<?> entityClass, KEY key) {
        return "cache-" + entityClass.getSimpleName() + "-" + key;
    }

    public static <KEY extends Serializable> Serializable getTagBindingKeyByClass(Class<?> entityClass, KEY tagId) {
        return "tag-binding-" + entityClass.getSimpleName() + "-" + tagId;
    }

    public static <KEY extends Serializable> Serializable getEntityBindingKeyByClass(Class<?> entityClass, KEY entityId) {
        return "entity-binding-" + entityClass.getSimpleName() + "-" + entityId;
    }

    public static Serializable getPageKey(Class<?> entityClass,long pageNum,long pageSize) {
        return "page-" + entityClass.getSimpleName() + "-" + pageNum + "-" + pageSize;
    }

    public static <Entity extends BaseEntity> Serializable getListKey(Class<Entity> entityClass) {
        return "list-" + entityClass.getSimpleName();
    }

    public static Serializable getPageKeyWithWrapper(Class<?> entityClass, long pageNum, long pageSize, Object hashValue) {
        return "page-" + entityClass.getSimpleName() + "-" + pageNum + "-" + pageSize + "-" + hashValue;
    }

    public static Serializable getListWithWrapper(Class<?> entityClass,Object hashValue) {
        return "list-" + entityClass.getSimpleName() + "-" + hashValue;
    }
}
