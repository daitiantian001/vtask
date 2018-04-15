package com.lmnml.group.entity.response;

import lombok.Data;

@Data
public class BaseR {
    private Integer result;
    private String exception;
    private Object data;

    public BaseR(Integer result) {
        this.result = result;
    }

    public BaseR(Integer result, String exception) {
        this.result = result;
        this.exception = exception;
    }
    public BaseR(Integer result, Object data) {
        this.result = result;
        this.data = data;
    }
}
