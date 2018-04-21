package com.lmnml.group.common.model;

/**
 * 响应code,msg
 * Created by daitian on 2018/4/17.
 */
public enum R {
    SUCCESS(1, "请求成功"),
    NET_ERROR(-1, "网络异常"),
    MYTEST(0,"请求失败"),
    PARAM_ER(10001,"参数错误");
    private int code;
    private String msg;

    R(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
