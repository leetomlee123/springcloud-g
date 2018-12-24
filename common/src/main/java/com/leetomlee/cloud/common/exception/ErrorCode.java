package com.leetomlee.cloud.common.exception;

public enum ErrorCode {
    /**
     * 1xx
     */
    DEMO("1001", "你说什么"), ONE("1002", "笑嘻嘻");

    private String code;

    private String msg;

    ErrorCode(String s, String msg) {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
