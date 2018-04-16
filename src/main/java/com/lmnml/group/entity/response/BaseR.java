package com.lmnml.group.entity.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseR {
    private Integer result;
    private String exception;
    private Object data;
    private Integer total;

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

    public BaseR(Integer result, Object data, Integer total) {
        this.result = result;
        this.data = data;
        this.total = total;
    }
}
