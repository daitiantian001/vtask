package com.lmnml.group.controller.app;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by daitian on 2018/4/15.
 */
@RestController
@RequestMapping("user")
@Api(value = "用户controller", tags = {"app接口"}, description = "调用写到x-token")
public class UserController {

    //登录
    @PostMapping("login")
    public void userLogin(String password,String moblie){
        System.out.println("用户登录");
    }
}
