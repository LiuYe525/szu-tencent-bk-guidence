package com.szuse.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author LiuYe
 * @version 1.0
 * @date 24/4/2023 下午3:21
 */
@FeignClient(name = "bk-guidance", contextId = "bk-guidance-user", url = "https://apps.ce.bktencent.com", path = "/bkguidance/user")
public interface BkClient {
    @GetMapping("/sendMsg/{msg}")
    String sendMsg(@PathVariable String msg);

    @GetMapping("/getMsg")
    String getMsg();
}
