package com.szuse.common.base.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

/**
 * @author LiuYe
 * @version 1.0
 * @date 4/4/2023 下午8:31
 * EntityIdType 实体Id类型
 */
@SuppressWarnings("rawtypes")
public abstract class BaseEntity<EntityIdType extends Serializable> {
    @JsonIgnore
    @TableField(exist = false)
    private Double score;
    @JsonIgnore
    public Double getScore() {
        return score;
    }
    @JsonIgnore
    public abstract EntityIdType getEntityId();
    public abstract BaseEntity<EntityIdType> createInstance(Object eid);
    @JsonIgnore
    public abstract String getEntityIdField();
}
