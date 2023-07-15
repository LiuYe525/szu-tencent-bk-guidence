package com.szuse.bkguidance.controller;

import static com.szuse.bkguidance.api.ResponseCodeEnum.*;

import com.szuse.bkguidance.api.CommonResponse;
import com.szuse.bkguidance.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author LiuYe
 * @version 1.0
 * @date 15/7/2023 下午5:00
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

  private final UserService userService;

  @GetMapping("/list")
  public CommonResponse getUserList(String email) {
    System.out.println("receive");
    var userList = userService.listUserByEmail(email);
    if (ObjectUtils.isEmpty(userList)) {
      return CommonResponse.builder().build(NOT_FOUND);
    }
    return CommonResponse.builder().data(userList).build(OK);
  }
}
