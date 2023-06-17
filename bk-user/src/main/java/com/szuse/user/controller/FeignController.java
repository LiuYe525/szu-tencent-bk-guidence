package com.szuse.user.controller;

import com.szuse.feign.client.BkClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author LiuYe
 * @version 1.0
 * @date 24/4/2023 下午3:23
 */
@RestController
@RequestMapping("/feign")
public class FeignController {

    private final BkClient bkClient;

    public FeignController(BkClient bkClient) {
        this.bkClient = bkClient;
    }

    @GetMapping("/sendMsg/{msg}")
    public String senMsg(@PathVariable String msg) {
        return bkClient.sendMsg(msg);
    }

    @GetMapping("/getMsg")
    public String getMsg() {
        return bkClient.getMsg();
    }
}
