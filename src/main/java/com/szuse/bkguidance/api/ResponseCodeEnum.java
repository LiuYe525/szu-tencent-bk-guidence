package com.szuse.bkguidance.api;

import lombok.AllArgsConstructor;

/**
 * @author LiuYe
 * @version 1.0
 * @date 15/7/2023 下午11:42
 */

@AllArgsConstructor
public enum ResponseCodeEnum {
    OK(true, 200, "成功"), NOT_FOUND(false, 404, "找不到请求的资源"), ERROR(false, 400, "失败");

    public final Boolean SUCCESS;
    public final Integer CODE;
    public final String TEXT;
}
