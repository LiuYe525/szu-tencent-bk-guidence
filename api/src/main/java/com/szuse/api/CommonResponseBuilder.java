package com.szuse.api;

public class CommonResponseBuilder {

    CommonResponse response;

    public CommonResponseBuilder() {
        response = new CommonResponse();
    }

    public CommonResponse build() {
        return response;
    }

    public CommonResponse build(ResponseCodeEnum codeEnum) {
        response.setSuccess(codeEnum.SUCCESS);
        response.setCode(codeEnum.CODE);
        response.setMsg(codeEnum.TEXT);
        return response;
    }

    public CommonResponseBuilder success(Boolean success) {
        response.setSuccess(success);
        return this;
    }

    public CommonResponseBuilder code(Integer code) {
        response.setCode(code);
        return this;
    }

    public CommonResponseBuilder msg(String msg) {
        response.setMsg(msg);
        return this;
    }

    public CommonResponseBuilder data(Object data) {
        response.setData(data);
        return this;
    }

    public CommonResponseBuilder appendData(String key, Object data) {
        response.getAppendData().put(key, data);
        return this;
    }
}
