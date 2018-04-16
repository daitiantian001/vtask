package com.lmnml.group.controller.app;

import com.lmnml.group.controller.BaseController;
import com.lmnml.group.entity.app.MsgCode;
import com.lmnml.group.entity.app.VPlatformUser;
import com.lmnml.group.service.app.IUserService;
import com.lmnml.group.util.AliyunSms;
import com.lmnml.group.util.MD5;
import com.lmnml.group.util.StrKit;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Created by daitian on 2018/4/15.
 */
@RestController
@RequestMapping("app/user")
@Api(value = "用户controller", tags = {"app接口"}, description = "app用户接口")
public class UserController extends BaseController {

    @Autowired
    private IUserService userService;

    @ApiOperation(value = "app用户登录")
    @PostMapping("login")
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
            @ApiResponse(code = 1, message = "失败")
    })
    public Object userLogin(String password, String mobile) {

        //参数校验
        if (StrKit.isBlank(password) || StrKit.isBlank(mobile)) {
            return R.BAD_REQ();
        }

        //查询用户
        VPlatformUser vPlatformUser = userService.findUserByMobile(mobile);
        if (vPlatformUser == null) {
            return R.NO("该手机号未注册!");
        }
        if (!MD5.Byte32(password).equals(vPlatformUser.getPassword())) {
            return R.NO("密码错误!");
        }
        return R.OK(vPlatformUser);
    }

    @ApiOperation(value = "app用户注册", notes = "失败获取exception中信息")
    @PostMapping("register")
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
            @ApiResponse(code = 1, message = "失败")
    })
    public Object userRegister(String mobile, String msgCode, String password, String inventCode) {
        //TODO 校验
        //查询用户
        VPlatformUser vPlatformUser = userService.findUserByMobile(mobile);
        if (vPlatformUser != null) {
            return R.NO("该手机号已注册!");
        }
        String code = userService.findMsgCode(mobile);
        if (!msgCode.equals(code)) {
            return R.NO("验证码错误!");
        }
        vPlatformUser = new VPlatformUser();
        vPlatformUser.setId(StrKit.ID());
        vPlatformUser.setAccount(0);
        vPlatformUser.setCreateTime(new Date());
        vPlatformUser.setPassword(MD5.Byte32(password));
        vPlatformUser.setInventCode(inventCode);
        vPlatformUser.setName("赚客_" + mobile);
        vPlatformUser.setPhoto("http://yuejinimg.oss-cn-beijing.aliyuncs.com/app_icon_default_photo.png");
        vPlatformUser.setMobile(mobile);
        vPlatformUser.setSex(0);
        userService.insertUser(vPlatformUser);
        return R.OK();
    }


    @PostMapping(value = "msgCode")
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
            @ApiResponse(code = 1, message = "失败")
    })
    @ApiOperation(value = "获取验证码", notes = "失败获取exception中信息")
    public Object sendMsg(String mobile) {

        //先查询
        VPlatformUser vPlatformUser = userService.findUserByMobile(mobile);
        if (vPlatformUser != null) {
            return R.NO("该手机号已注册!");
        }

        Boolean flag = userService.canSend(mobile);

        if (flag == null || flag) {
            String code=null;
            try {
               code = AliyunSms.sendSms(mobile);
            } catch (Exception e) {
               return R.NO("阿里云通讯异常");
            }
            if(StrKit.isBlank(code)){
                return R.NO("阿里云通讯异常");
            }
            //存储msgCode
            MsgCode msgCode = new MsgCode(mobile, code, new Date());
            userService.insertMsgCode(msgCode);
            return R.OK();
        }
        return R.NO("请两分钟后再发送!");

    }
}
