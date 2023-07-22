package com.szuse.user.controller;


import static com.szuse.api.ResponseCodeEnum.NOT_FOUND;
import static com.szuse.api.ResponseCodeEnum.OK;

import com.szuse.api.CommonResponse;
import com.szuse.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author LiuYe
 * @version 1.0
 * @date 15/7/2023 下午5:00
 */
@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/list")
    public CommonResponse getUserList(String email) {
        log.info("get user list with email {}", email);
        var userList = userService.listUserByEmail(email);
        if (ObjectUtils.isEmpty(userList)) {
            return CommonResponse.builder().build(NOT_FOUND);
        }
        return CommonResponse.builder().data(userList).build(OK);
    }
}