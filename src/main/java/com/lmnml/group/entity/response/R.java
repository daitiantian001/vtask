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
    public BaseR NO(String s) {
        return new BaseR(FAILTURE, s);
    }
}
