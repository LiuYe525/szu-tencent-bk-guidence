package com.szuse.user.dto;

import com.szuse.user.entity.User;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotBlank;

/**
 * @author LiuYe
 * @version 1.0
 * @date 2/6/2023 下午9:52
 */
public class UserDTO {
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String email;
    private Integer age;

    public User dtoToEntity() {
        User user = new User();
        BeanUtils.copyProperties(this,user);
        return user;
    }
}
