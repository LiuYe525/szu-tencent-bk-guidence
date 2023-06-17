package com.szuse.common.base.result;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.szuse.common.base.enums.ResultCode;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author LiuYe
 */
@Data
@Accessors(chain = true)
public class R<T> implements Serializable {
    // 返回编号
    private Integer code;
    // 错误信息
    private String msg;
    // 数据
    @JsonUnwrapped
    private T data;
    // 附加数据
    @JsonUnwrapped
    private Map<String, Object> attachData;

    private R() {
        this.data = null;
        attachData = null;
    }

    public static R success() {
        return new R()
                .code(ResultCode.DEFAULT_SUCCESS)
                .msg("请求成功");
    }

    public static R error() {
        return new R()
                .code(ResultCode.DEFAULT_FAULT)
                .msg("请求失败");
    }

    public R code(ResultCode code) {
        this.setCode(code.getCode());
        return this;
    }

    public R msg(String msg) {
        this.setMsg(msg);
        return this;
    }

    public R data(T data) {
        this.data = data;
        return this;
    }

    public R attachData(String key, Object data) {
        if (null == attachData) {
            attachData = new HashMap<>();
        }
        this.attachData.put(key, data);
        return this;
    }
}
