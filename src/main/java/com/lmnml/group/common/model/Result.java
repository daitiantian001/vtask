package com.lmnml.group.common.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

/**
 * 响应实体类
 * Created by daitian on 2018/4/17.
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result<T> {
    private int result;
    private String exception;
    private T data;

    public Result() {
    }

    public Result(int result, String exception) {
        this.result = result;
        this.exception = exception;
    }

    public Result(String exception) {
        this.result=0;
        this.exception = exception;
    }

    public Result(R r) {
        this.result = r.getCode();
        this.exception = r.getMsg();
    }

    public Result(R r, T data) {
        this(r);
        this.data = data;
    }


}
