package com.lmnml.group.controller.app;

import com.lmnml.group.controller.BaseController;
import com.lmnml.group.entity.app.VPlatformUser;
import com.lmnml.group.service.app.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by daitian on 2018/4/15.
 */
@RestController
@RequestMapping("user")
@Api(value = "用户controller", tags = {"app接口"}, description = "app用户接口")
public class UserController extends BaseController {

    @Autowired
    private IUserService userService;

    //登录
    @ApiOperation(value = "App登录")
    @PostMapping("login")
    @ApiResponses({
            @ApiResponse(code = 1, message = "成功"),
            @ApiResponse(code = 0, message = "失败")
    })
    public Object userLogin(String password, String mobile) {

        //查询用户
//        VPlatformUser vPlatformUser = userService.findUserByMobile(mobile);
//        if(vPlatformUser==null){
//            return R.NO("用户名或密码错误");
//        }
//        return R.OK(vPlatformUser);
    }

}
