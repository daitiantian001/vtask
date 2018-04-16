package com.lmnml.group.entity.response;

/**
 * Created by daitian on 2018/3/13.
 */
public interface IR {
    Integer SUCCESS=1;
    Integer FAILTURE=0;
    Integer UNLOGIN=-1;

    Object OK();
    Object OK(Object o);
    Object OK_NUM(Integer num,Object o);
    Object NO(String s);
    Object BAD_REQ();
}
