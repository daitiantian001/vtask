package com.lmnml.group.entity.response;

public class R implements IR {
    @Override
    public BaseR OK() {
        return new BaseR(SUCCESS);
    }

    @Override
    public Object OK(Object o) {
        return new BaseR(SUCCESS, o);
    }

    @Override
    public Object OK_NUM(Integer num,Object o) {
        return new BaseR(SUCCESS, o,num);
    }

    @Override
    public BaseR NO(String s) {
        return new BaseR(FAILTURE, s);
    }

    @Override
    public Object BAD_REQ() {
        return new BaseR(FAILTURE,"请求参数错误!");
    }

}
