package com.szuse.common.base.enums;

/**
 * @author LiuYe
 * @version 1.0
 * @date 23/5/2023 下午8:15
 */
public enum ResultCode {
    DEFAULT_SUCCESS(200),
    DEFAULT_FAULT(400),
    NOT_FOUND(404);

    private final int code;

    ResultCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
