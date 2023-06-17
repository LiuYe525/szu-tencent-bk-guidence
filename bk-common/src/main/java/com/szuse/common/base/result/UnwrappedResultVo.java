package com.szuse.common.base.result;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.szuse.common.base.enums.ResultCode;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("rawtypes")
@Data
public class UnwrappedResultVo<T> {
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

    private UnwrappedResultVo() {
        this.data = null;
        attachData = null;
    }

    public static UnwrappedResultVo success() {
        return new UnwrappedResultVo()
                .code(ResultCode.DEFAULT_SUCCESS)
                .msg("请求成功");
    }

    public static UnwrappedResultVo error() {
        return new UnwrappedResultVo()
                .code(ResultCode.DEFAULT_FAULT)
                .msg("请求失败");
    }

    public UnwrappedResultVo code(ResultCode code) {
        this.setCode(code.getCode());
        return this;
    }

    public UnwrappedResultVo msg(String msg) {
        this.setMsg(msg);
        return this;
    }

    public UnwrappedResultVo data(T data) {
        this.data = data;
        return this;
    }

    public UnwrappedResultVo attachData(String key, Object data) {
        if (null == attachData) {
            attachData = new HashMap<>();
        }
        this.attachData.put(key, data);
        return this;
    }
}