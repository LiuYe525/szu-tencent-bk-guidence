package com.szuse.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.szuse.common.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("users")
@NoArgsConstructor
public class User extends BaseEntity<Long> {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String name;
    private String email;
    private Integer age;

    @Override
    public Long getEntityId() {
        return id;
    }

    @Override
    public BaseEntity<Long> createInstance(Object eid) {
        User user = new User();
        user.setId(Long.parseLong(String.valueOf(eid)));
        return user;
    }

    @Override
    public String getEntityIdField() {
        return "id";
    }
}
